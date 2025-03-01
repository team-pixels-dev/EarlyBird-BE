package earlybird.earlybird.appointment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @deprecated Appointment 관련 클래스는 2024년 하반기 베타테스트에서 사용함
 */
@Deprecated
@Getter
@RequiredArgsConstructor
public enum AppointmentUpdateType {
    POSTPONE("약속 미루기"),
    MODIFY("일정 수정하기");

    private final String type;
}
