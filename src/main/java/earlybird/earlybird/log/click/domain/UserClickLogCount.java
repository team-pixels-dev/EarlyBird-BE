package earlybird.earlybird.log.click.domain;

import earlybird.earlybird.common.BaseTimeEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Table(
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "UniqueClickTypeAndClientIdAndClickDate",
                    columnNames = {
                        "user_click_log_count_click_type",
                        "user_click_log_count_client_id",
                        "user_click_log_count_click_date"
                    })
        })
@Entity
public class UserClickLogCount extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_click_log_count_id")
    private Long id;

    @NotBlank
    @Column(name = "user_click_log_count_click_type")
    private String clickType;

    @NotBlank
    @Column(name = "user_click_log_count_client_id")
    private String clientId;

    @Column(name = "user_click_log_count_click_count")
    private Long clickCount = 0L;

    @NotNull
    @Column(name = "user_click_log_count_click_date")
    private LocalDate clickDate;

    @Builder
    public UserClickLogCount(String clickType, String clientId, LocalDate clickDate) {
        this.clickType = clickType;
        this.clientId = clientId;
        this.clickDate = clickDate;
    }

    public void increaseClickCount() {
        this.clickCount++;
    }
}
