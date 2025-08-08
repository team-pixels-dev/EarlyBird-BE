package earlybird.earlybird.error.exception.auth.apple;

import earlybird.earlybird.error.ErrorCode;
import earlybird.earlybird.error.exception.BusinessBaseException;

public class VerifyAppleIdTokenException extends BusinessBaseException {
    public VerifyAppleIdTokenException() {
        super(ErrorCode.VERIFY_APPLE_ID_TOKEN_EXCEPTION);
    }
}
