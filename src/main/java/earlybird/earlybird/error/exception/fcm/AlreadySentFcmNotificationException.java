package earlybird.earlybird.error.exception.fcm;

import earlybird.earlybird.error.exception.BusinessBaseException;

import static earlybird.earlybird.error.ErrorCode.ALREADY_SENT_FCM_NOTIFICATION;

public class AlreadySentFcmNotificationException extends BusinessBaseException {
    public AlreadySentFcmNotificationException() {
        super(ALREADY_SENT_FCM_NOTIFICATION);
    }
}
