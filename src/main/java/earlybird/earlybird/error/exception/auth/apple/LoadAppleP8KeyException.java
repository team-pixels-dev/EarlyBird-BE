package earlybird.earlybird.error.exception.auth.apple;

import earlybird.earlybird.error.ErrorCode;
import earlybird.earlybird.error.exception.BusinessBaseException;

public class LoadAppleP8KeyException extends BusinessBaseException {

    public LoadAppleP8KeyException() {
        super(ErrorCode.LOAD_APPLE_P8_KEY_EXCEPTION);
    }
}
