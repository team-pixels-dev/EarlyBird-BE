package earlybird.earlybird.email.address.save.entity;

import earlybird.earlybird.common.BaseTimeEntity;

import jakarta.persistence.*;

import lombok.*;

/** 마케팅을 통해 수집한 이메일 주소를 저장하는 엔티티 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "email_address_for_marketing")
@Entity
public class MarketingEmailAddress extends BaseTimeEntity {

    @Column(name = "email_address_for_marketing_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 이메일 주소
    @Column(name = "email_address_for_marketing_email", nullable = false)
    private String email;

    // 이메일 수집 출처 이벤트
    @Enumerated(EnumType.STRING)
    @Column(name = "email_address_for_marketing_source_event")
    private MarketingEvent sourceEvent;
}
