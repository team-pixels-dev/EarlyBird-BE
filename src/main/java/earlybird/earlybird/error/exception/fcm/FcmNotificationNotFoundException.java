package earlybird.earlybird.error.exception.fcm;

import earlybird.earlybird.error.ErrorCode;
import earlybird.earlybird.error.exception.NotFoundException;

public class FcmNotificationNotFoundException extends NotFoundException {
    public FcmNotificationNotFoundException() {
        super(ErrorCode.NOTIFICATION_NOT_FOUND);
    }
}
