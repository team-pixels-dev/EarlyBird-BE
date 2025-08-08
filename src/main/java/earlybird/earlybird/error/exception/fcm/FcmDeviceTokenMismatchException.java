package earlybird.earlybird.error.exception.fcm;

import static earlybird.earlybird.error.ErrorCode.FCM_DEVICE_TOKEN_MISMATCH;

import earlybird.earlybird.error.exception.BusinessBaseException;

public class FcmDeviceTokenMismatchException extends BusinessBaseException {

    public FcmDeviceTokenMismatchException() {
        super(FCM_DEVICE_TOKEN_MISMATCH);
    }
}
