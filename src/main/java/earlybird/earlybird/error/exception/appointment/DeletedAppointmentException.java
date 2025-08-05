package earlybird.earlybird.error.exception.appointment;

import earlybird.earlybird.error.exception.BusinessBaseException;

import static earlybird.earlybird.error.ErrorCode.DELETED_APPOINTMENT_EXCEPTION;

public class DeletedAppointmentException extends BusinessBaseException {
    public DeletedAppointmentException() {
        super(DELETED_APPOINTMENT_EXCEPTION);
    }
}
