package earlybird.earlybird.error.exception.fcm;

import static earlybird.earlybird.error.ErrorCode.FCM_MESSAGE_TIME_BEFORE_NOW;

import earlybird.earlybird.error.exception.BusinessBaseException;

public class FcmMessageTimeBeforeNowException extends BusinessBaseException {

    public FcmMessageTimeBeforeNowException() {
        super(FCM_MESSAGE_TIME_BEFORE_NOW);
    }
}
