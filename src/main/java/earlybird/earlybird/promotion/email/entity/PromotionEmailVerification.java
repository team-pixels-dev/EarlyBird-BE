package earlybird.earlybird.promotion.email.entity;

import earlybird.earlybird.common.BaseTimeEntity;
import earlybird.earlybird.promotion.entity.PromotionCampaign;
import earlybird.earlybird.promotion.entity.PromotionUrlUuid;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 프로모션 이메일 인증 내역을 관리하는 엔티티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "promotion_email_verifications")
@Entity
public class PromotionEmailVerification extends BaseTimeEntity {

    @Column(name = "promotion_email_verifications_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 소속 프로모션 캠페인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_campaigns_id", nullable = false)
    private PromotionCampaign promotionCampaign;

    // 클라이언트 이메일로 전송한 URL 주소에 들어 있는 UUID
    @OneToOne
    @JoinColumn(name = "promotion_url_uuid_promotion_url_uuids_id", nullable = false)
    private PromotionUrlUuid promotionUrlUuid;

    // 인증 대상 이메일
    @Column(name = "promotion_email_verifications_email", nullable = false)
    private String email;

    // 인증 성공 여부
    @Setter
    @Column(name = "promotion_email_verifications_is_success", nullable = false)
    private Boolean verificationIsSuccess;

    // 프로모션 코드 발급 정보
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_promotion_code_issuances_id", nullable = false)
    private EmailPromotionCodeIssuance emailPromotionCodeIssuance;

    // 시간대 : 한국
    // 인증 이메일 전송 일시
    @Setter
    @Column(name = "promotion_email_verification_email_sent_at")
    private LocalDateTime sentAt;

    // 시간대 : 한국
    // 인증 전에는 NULL
    @Setter
    @Column(name = "promotion_email_verifications_verfied_at")
    private LocalDateTime verifiedAt;
}
