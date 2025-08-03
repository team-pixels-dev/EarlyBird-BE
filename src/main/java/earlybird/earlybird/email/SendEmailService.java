package earlybird.earlybird.email;

import static earlybird.earlybird.promotion.email.entity.PromotionEmailMessageType.BERKELEY_6_MONTH_FREE;

import earlybird.earlybird.common.util.LocalDateTimeUtil;
import earlybird.earlybird.promotion.email.entity.PromotionEmailMessageType;
import earlybird.earlybird.promotion.email.entity.PromotionEmailVerification;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SendEmailService {

    @Value("${test.mail.password}")
    private String mailPassword;

    private final JavaMailSender javaMailSender;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000))
    @Async
    public void send(
            PromotionEmailVerification promotionEmailVerification,
            PromotionEmailMessageType promotionEmailMessageType) {
        try {
            log.info("mailPassword = " + mailPassword);
            MimeMessage message = javaMailSender.createMimeMessage();

            message.addRecipients(Message.RecipientType.TO, promotionEmailVerification.getEmail());
            message.setFrom("earlybirdteam2024@gmail.com");

            message.setSubject(promotionEmailMessageType.getTitle());

            String verificationUrl = getVerificationUrl(promotionEmailVerification);

            String messageText = getMessageText(promotionEmailMessageType, verificationUrl);

            message.setText(messageText, "utf-8", "html");

            javaMailSender.send(message);
            promotionEmailVerification.setSentAt(LocalDateTimeUtil.getLocalDateTimeNow());

            log.info(
                    "Promotion code email sent successfully to: {}",
                    promotionEmailVerification.getEmail());

        } catch (Exception e) {
            log.error(
                    "Failed to send promotion code email to: {}",
                    promotionEmailVerification.getEmail(),
                    e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private static String getMessageText(
            PromotionEmailMessageType promotionEmailMessageType, String verificationUrl) {

        if (promotionEmailMessageType.equals(BERKELEY_6_MONTH_FREE))
            return String.format(
                    promotionEmailMessageType.getMessageText(), verificationUrl, verificationUrl);
        else throw new IllegalArgumentException();
    }

    private String getVerificationUrl(PromotionEmailVerification promotionEmailVerification) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://earlybirdteam.com/promotion/apple/univ/email?code=");
        stringBuilder.append(promotionEmailVerification.getPromotionUrlUuid().getUuid());
        return stringBuilder.toString();
    }
}
