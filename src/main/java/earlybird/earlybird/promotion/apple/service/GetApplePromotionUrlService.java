package earlybird.earlybird.promotion.apple.service;

import earlybird.earlybird.common.util.LocalDateTimeUtil;
import earlybird.earlybird.error.exception.ApplePromotionUrlListIsEmptyException;
import earlybird.earlybird.promotion.apple.ApplePromotionUrl;
import earlybird.earlybird.promotion.apple.ApplePromotionUrlRepository;
import earlybird.earlybird.promotion.apple.service.response.GetApplePromotionUrlServiceResponse;
import earlybird.earlybird.promotion.email.entity.PromotionEmailVerification;
import earlybird.earlybird.promotion.email.repository.PromotionEmailVerificationRepository;
import earlybird.earlybird.promotion.entity.PromotionUrlUuid;
import earlybird.earlybird.promotion.repository.PromotionUrlUuidRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GetApplePromotionUrlService {

    private final ApplePromotionUrlRepository applePromotionUrlRepository;
    private final PromotionEmailVerificationRepository promotionEmailVerificationRepository;
    private final PromotionUrlUuidRepository promotionUrlUuidRepository;

    @Transactional
    public GetApplePromotionUrlServiceResponse getPromotionUrl(Long promotionCampaignId) {

        List<ApplePromotionUrl> urls =
                applePromotionUrlRepository.findAllByIsUsedFalseAndPromotionCampaignId(
                        promotionCampaignId);

        if (urls.isEmpty()) {
            throw new ApplePromotionUrlListIsEmptyException();
        }

        ApplePromotionUrl promotionUrl =
                urls.stream()
                        .filter(
                                url ->
                                        url.getExpiredAt()
                                                .isAfter(LocalDateTimeUtil.getLocalDateTimeNow()))
                        .findFirst()
                        .orElseThrow(ApplePromotionUrlListIsEmptyException::new);

        promotionUrl.setUsed();
        return new GetApplePromotionUrlServiceResponse(promotionUrl.getUrl());
    }

    @Transactional
    public GetApplePromotionUrlServiceResponse getPromotionUrlByUuid(String promotionCodeUuid) {

        UUID uuid = UUID.fromString(promotionCodeUuid);

        PromotionUrlUuid promotionUrlUuid =
                promotionUrlUuidRepository.findByUuid(uuid).orElseThrow();

        PromotionEmailVerification promotionEmailVerification =
                promotionEmailVerificationRepository
                        .findByPromotionUrlUuid(promotionUrlUuid)
                        .orElseThrow();

        String promotionUrl =
                promotionEmailVerification.getEmailPromotionCodeIssuance().getPromotionCode();

        if (!promotionEmailVerification.getVerificationIsSuccess()) {
            setStatusToSuccess(promotionEmailVerification);
            setUsed(promotionUrl);
        }

        return GetApplePromotionUrlServiceResponse.builder().promotionUrl(promotionUrl).build();
    }

    private void setUsed(String promotionUrl) {
        ApplePromotionUrl applePromotionUrl =
                applePromotionUrlRepository.findByUrl(promotionUrl).orElseThrow();
        applePromotionUrl.setUsed();
    }

    private void setStatusToSuccess(PromotionEmailVerification promotionEmailVerification) {
        promotionEmailVerification.setVerifiedAt(LocalDateTimeUtil.getLocalDateTimeNow());
        promotionEmailVerification.setVerificationIsSuccess(true);
    }
}
