package earlybird.earlybird.promotion.entity;

import earlybird.earlybird.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * 프로모션 URL 주소에 들어가는 UUID를 관리하는 엔티티.
 * 한번 사용한 UUID는 다시 사용하면 안 된다.
 * 혹시 모를 중복에 대비하기 위한 엔티티.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "promotion_url_uuids")
@Entity
public class PromotionUrlUuid extends BaseTimeEntity {

    @Column(name = "promotion_url_uuids_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "promotion_url_uuids_uuid", nullable = false)
    private UUID uuid;
}
