package earlybird.earlybird.error.exception.fcm;

import static earlybird.earlybird.error.ErrorCode.ALREADY_SENT_FCM_NOTIFICATION;

import earlybird.earlybird.error.exception.BusinessBaseException;

public class AlreadySentFcmNotificationException extends BusinessBaseException {
    public AlreadySentFcmNotificationException() {
        super(ALREADY_SENT_FCM_NOTIFICATION);
    }
}
