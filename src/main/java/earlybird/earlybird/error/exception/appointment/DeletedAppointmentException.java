package earlybird.earlybird.error.exception.appointment;

import static earlybird.earlybird.error.ErrorCode.DELETED_APPOINTMENT_EXCEPTION;

import earlybird.earlybird.error.exception.BusinessBaseException;

public class DeletedAppointmentException extends BusinessBaseException {
    public DeletedAppointmentException() {
        super(DELETED_APPOINTMENT_EXCEPTION);
    }
}
