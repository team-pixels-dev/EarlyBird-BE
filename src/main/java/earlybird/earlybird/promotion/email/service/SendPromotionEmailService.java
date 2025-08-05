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
        /** TODO - 락을 걸었는데 이거 성능 문제 좀 더 고민 필요 - 스프링 트랜잭션에 대한 고민 필요 - 메일 발송 부분 로직 문제 없나 다시 체크 */
        checkEmailAddress(request);

        PromotionCampaign promotionCampaign =
                getPromotionCampaignService.findById(request.getPromotionCampaignId());

        promotionEmailVerificationRepository
                .findByPromotionCampaignAndEmail(promotionCampaign, request.getEmail())
                .ifPresentOrElse(
                        verification -> sendEmailIfSendBefore(verification, request),
                        () -> sendFirstEmail(promotionCampaign, request));
    }

    // 이전에 동일한 캠패인, 동일한 이메일로 인증 메일을 전송한 적이 있으면 이 함수 호출
    private void sendEmailIfSendBefore(
            PromotionEmailVerification promotionEmailVerification,
            SendVerificationEmailServiceRequest request) {
        sendEmailService.send(promotionEmailVerification, request.getPromotionEmailMessageType());
    }

    // 처음 인증 요청하는 건 이 함수 호출
    private void sendFirstEmail(
            PromotionCampaign promotionCampaign, SendVerificationEmailServiceRequest request) {
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
