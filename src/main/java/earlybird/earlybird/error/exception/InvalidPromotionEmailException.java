package earlybird.earlybird.error.exception;

import earlybird.earlybird.error.ErrorCode;

public class InvalidPromotionEmailException extends BusinessBaseException {
    public InvalidPromotionEmailException() {
        super(ErrorCode.INVALID_PROMOTION_EMAIL_EXCEPTION);
    }
}
