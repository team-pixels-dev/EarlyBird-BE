package earlybird.earlybird.task.domain;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@SQLRestriction("task_is_deleted = false")
@SQLDelete(sql = "UPDATE task SET task_is_deleted = true WHERE task_id = ?")
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Setter
    @Column(name = "task_title", nullable = false)
    private String title;

    @Setter
    @Column(name = "task_start_time", nullable = false)
    private LocalDateTime startTime;

    @Setter
    @Column(name = "task_alarm_is_on", nullable = false)
    private Boolean isAlarmOn;

    @Setter
    @Column(name = "task_vibration_is_on", nullable = false)
    private Boolean isVibrationOn;

    @Column(name = "task_is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Builder
    public Task(
            String clientId,
            String title,
            LocalDateTime startTime,
            Boolean isAlarmOn,
            Boolean isVibrationOn) {
        this.clientId = clientId;
        this.title = title;
        this.startTime = startTime;
        this.isAlarmOn = isAlarmOn;
        this.isVibrationOn = isVibrationOn;
    }
}
