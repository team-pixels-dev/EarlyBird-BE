package earlybird.earlybird.appointment.controller.response;

import earlybird.earlybird.appointment.service.response.CreateAppointmentServiceResponse;

import lombok.*;

/**
 * @deprecated Appointment 관련 클래스는 2024년 하반기 베타테스트에서 사용함
 */
@Deprecated
@Getter
public class CreateAppointmentResponse {
    private final Long createdAppointmentId;

    @Builder
    private CreateAppointmentResponse(Long createdAppointmentId) {
        this.createdAppointmentId = createdAppointmentId;
    }

    public static CreateAppointmentResponse from(
            CreateAppointmentServiceResponse createAppointmentServiceResponse) {
        return CreateAppointmentResponse.builder()
                .createdAppointmentId(createAppointmentServiceResponse.getCreatedAppointmentId())
                .build();
    }
}
