package earlybird.earlybird.appointment.service;

import earlybird.earlybird.appointment.domain.Appointment;
import earlybird.earlybird.appointment.domain.AppointmentRepository;
import earlybird.earlybird.appointment.service.request.CreateAppointmentServiceRequest;
import earlybird.earlybird.appointment.service.response.CreateAppointmentServiceResponse;
import earlybird.earlybird.common.util.LocalDateTimeUtil;
import earlybird.earlybird.scheduler.notification.domain.NotificationStep;
import earlybird.earlybird.scheduler.notification.service.NotificationInfoFactory;
import earlybird.earlybird.scheduler.notification.service.register.RegisterAllNotificationAtSchedulerService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @deprecated Appointment 관련 클래스는 2024년 하반기 베타테스트에서 사용함
 */
@Deprecated
@RequiredArgsConstructor
@Service
public class CreateAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final RegisterAllNotificationAtSchedulerService registerService;
    private final NotificationInfoFactory factory;

    @Transactional
    public CreateAppointmentServiceResponse create(CreateAppointmentServiceRequest request) {
        Appointment appointment = createAppointmentInstance(request);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        LocalDateTime firstAppointmentTime = request.getFirstAppointmentTime();
        LocalDateTime movingTime =
                LocalDateTimeUtil.subtractDuration(
                        firstAppointmentTime, request.getMovingDuration());
        LocalDateTime preparationTime =
                LocalDateTimeUtil.subtractDuration(movingTime, request.getPreparationDuration());

        Map<NotificationStep, Instant> notificationInfo =
                factory.createTargetTimeMap(preparationTime, movingTime, firstAppointmentTime);
        registerService.register(appointment, notificationInfo);

        return CreateAppointmentServiceResponse.builder()
                .createdAppointmentId(savedAppointment.getId())
                .build();
    }

    private Appointment createAppointmentInstance(CreateAppointmentServiceRequest request) {
        return Appointment.builder()
                .appointmentName(request.getAppointmentName())
                .clientId(request.getClientId())
                .deviceToken(request.getDeviceToken())
                .repeatingDayOfWeeks(request.getRepeatDayOfWeekList())
                .appointmentTime(request.getFirstAppointmentTime().toLocalTime())
                .preparationDuration(request.getPreparationDuration())
                .movingDuration(request.getMovingDuration())
                .build();
    }
}
