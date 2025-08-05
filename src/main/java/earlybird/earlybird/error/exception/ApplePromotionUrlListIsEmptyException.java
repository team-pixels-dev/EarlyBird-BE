package earlybird.earlybird.error.exception;

import earlybird.earlybird.error.ErrorCode;

public class ApplePromotionUrlListIsEmptyException extends BusinessBaseException {
    public ApplePromotionUrlListIsEmptyException() {
        super(ErrorCode.APPLE_PROMOTION_URL_LIST_IS_EMPTY_EXCEPTION);
    }
}
