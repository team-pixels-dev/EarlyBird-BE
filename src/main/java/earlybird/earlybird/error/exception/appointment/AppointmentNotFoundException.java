package earlybird.earlybird.error.exception.appointment;

import earlybird.earlybird.error.ErrorCode;
import earlybird.earlybird.error.exception.NotFoundException;

public class AppointmentNotFoundException extends NotFoundException {
    public AppointmentNotFoundException() {
        super(ErrorCode.APPOINTMENT_NOT_FOUND);
    }
}
