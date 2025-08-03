package earlybird.earlybird.promotion.email.service;

import earlybird.earlybird.email.SendEmailService;
import earlybird.earlybird.error.exception.InvalidPromotionEmailException;
import earlybird.earlybird.promotion.apple.service.GetApplePromotionUrlService;
import earlybird.earlybird.promotion.email.entity.EmailPromotionCodeIssuance;
import earlybird.earlybird.promotion.email.entity.PromotionEmailVerification;
import earlybird.earlybird.promotion.email.repository.EmailPromotionCodeIssuanceRepository;
import earlybird.earlybird.promotion.email.repository.PromotionEmailVerificationRepository;
import earlybird.earlybird.promotion.email.service.request.SendVerificationEmailServiceRequest;
import earlybird.earlybird.promotion.entity.PromotionCampaign;
import earlybird.earlybird.promotion.entity.PromotionUrlUuid;
import earlybird.earlybird.promotion.service.CreatePromotionUrlUuidService;
import earlybird.earlybird.promotion.service.GetPromotionCampaignService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SendPromotionEmailService {

    private final GetApplePromotionUrlService getApplePromotionUrlService;
    private final CheckEmailAddressService checkEmailAddressService;
    private final CreatePromotionUrlUuidService createPromotionUrlUuidService;
    private final GetPromotionCampaignService getPromotionCampaignService;
    private final SendEmailService sendEmailService;
    private final PromotionEmailVerificationRepository promotionEmailVerificationRepository;
    private final EmailPromotionCodeIssuanceRepository emailPromotionCodeIssuanceRepository;

    @Transactional
    public void sendVerificationEmail(SendVerificationEmailServiceRequest request) {
        /**
         * TODO - 이메일 유효성 검사 (완) - URL을 구성할 UUID 발급 (완) - DB에서 애플 프로모션 URL 하나 조회 - 락을 걸었는데 이거 성능 문제
         * 좀 더 고민 필요 - 이메일로 인증 URL 전송 (여기에 UUID가 들어감) - 비동기 처리 필요 (완) - no-reply@earlybirdteam.com
         * 이메일 적용 필요 - 인증 URL을 클릭하면 애플 프로모션 URL로 리다이렉션 (완) - 엣지 케이스 좀 더 고민 필요 - 스프링 트랜잭션에 대한 고민 필요 -
         * Retry 로직 도입 고려 (완) - 메일 발송 부분 로직 문제 없나 다시 체크
         */
        checkEmailAddress(request);

        PromotionCampaign promotionCampaign =
                getPromotionCampaignService.findById(request.getPromotionCampaignId());

        PromotionUrlUuid promotionUrlUuid = createPromotionUrlUuidService.create();
        String promotionUrl =
                getApplePromotionUrlService
                        .getPromotionUrl(promotionCampaign.getId())
                        .getPromotionUrl();

        EmailPromotionCodeIssuance emailPromotionCodeIssuance =
                createEmailPromotionCodeIssuance(promotionCampaign, promotionUrl);

        PromotionEmailVerification promotionEmailVerification =
                createPromotionEmailVerification(
                        promotionCampaign,
                        promotionUrlUuid,
                        request.getEmail(),
                        emailPromotionCodeIssuance);

        sendEmailService.send(promotionEmailVerification, request.getPromotionEmailMessageType());
    }

    private void checkEmailAddress(SendVerificationEmailServiceRequest request) {
        Boolean isValidDomain =
                checkEmailAddressService.checkValidPromotionEmail(
                        request.getEmail(), request.getPromotionCampaignId());

        if (!isValidDomain) {
            throw new InvalidPromotionEmailException();
        }
    }

    private EmailPromotionCodeIssuance createEmailPromotionCodeIssuance(
            PromotionCampaign promotionCampaign, String promotionCode) {

        EmailPromotionCodeIssuance emailPromotionCodeIssuance =
                EmailPromotionCodeIssuance.builder()
                        .promotionCampaign(promotionCampaign)
                        .promotionCode(promotionCode)
                        .promotionCodeIsUsed(false)
                        .build();

        return emailPromotionCodeIssuanceRepository.save(emailPromotionCodeIssuance);
    }

    private PromotionEmailVerification createPromotionEmailVerification(
            PromotionCampaign promotionCampaign,
            PromotionUrlUuid promotionUrlUuid,
            String email,
            EmailPromotionCodeIssuance emailPromotionCodeIssuance) {

        PromotionEmailVerification promotionEmailVerification =
                PromotionEmailVerification.builder()
                        .promotionCampaign(promotionCampaign)
                        .promotionUrlUuid(promotionUrlUuid)
                        .verificationIsSuccess(false)
                        .email(email)
                        .emailPromotionCodeIssuance(emailPromotionCodeIssuance)
                        .build();

        return promotionEmailVerificationRepository.save(promotionEmailVerification);
    }
}
