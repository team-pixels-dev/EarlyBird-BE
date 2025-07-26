package earlybird.earlybird.appointment.service;

import earlybird.earlybird.appointment.domain.Appointment;
import earlybird.earlybird.appointment.domain.AppointmentRepository;
import earlybird.earlybird.error.exception.appointment.AppointmentNotFoundException;
import earlybird.earlybird.error.exception.appointment.DeletedAppointmentException;
import earlybird.earlybird.scheduler.notification.service.deregister.request.DeregisterFcmMessageAtSchedulerServiceRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * @deprecated Appointment 관련 클래스는 2024년 하반기 베타테스트에서 사용함
 */
@Deprecated
@RequiredArgsConstructor
@Service
public class FindAppointmentService {

    private final AppointmentRepository appointmentRepository;

    public Appointment findBy(Long appointmentId, String clientId) {
        Appointment appointment =
                appointmentRepository
                        .findById(appointmentId)
                        .orElseThrow(AppointmentNotFoundException::new);

        if (!appointment.getClientId().equals(clientId)) throw new AppointmentNotFoundException();

        if (appointment.isDeleted()) throw new DeletedAppointmentException();

        return appointment;
    }

    public Appointment findBy(DeregisterFcmMessageAtSchedulerServiceRequest request) {
        return findBy(request.getAppointmentId(), request.getClientId());
    }
}
