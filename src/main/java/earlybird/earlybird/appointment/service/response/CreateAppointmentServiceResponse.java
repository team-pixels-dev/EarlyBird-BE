package earlybird.earlybird.appointment.service.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @deprecated Appointment 관련 클래스는 2024년 하반기 베타테스트에서 사용함
 */
@Deprecated
@Getter
public class CreateAppointmentServiceResponse {

    private final Long createdAppointmentId;

    @Builder
    private CreateAppointmentServiceResponse(Long createdAppointmentId) {
        this.createdAppointmentId = createdAppointmentId;
    }
}
