package earlybird.earlybird.log.visit.domain;

import earlybird.earlybird.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.NoArgsConstructor;

/** 로그인 도입 전 베타 테스트 단계에서만 사용 */
@NoArgsConstructor
@Entity
public class ClientId extends BaseTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "client_id_id")
    private Long id;

    @NotNull
    @Column(name = "client_id_client_id", unique = true)
    private String clientId;

    @Builder
    public ClientId(String clientId) {
        this.clientId = clientId;
    }
}
