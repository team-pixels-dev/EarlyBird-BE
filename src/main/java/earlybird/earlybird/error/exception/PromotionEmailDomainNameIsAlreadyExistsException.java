package earlybird.earlybird.error.exception;

import static earlybird.earlybird.error.ErrorCode.ALREADY_SENT_FCM_NOTIFICATION;
import static earlybird.earlybird.error.ErrorCode.PROMOTION_EMAIL_DOMAIN_NAME_IS_ALREADY_EXISTS_EXCEPTION;

public class PromotionEmailDomainNameIsAlreadyExistsException extends BusinessBaseException {
    public PromotionEmailDomainNameIsAlreadyExistsException() {
        super(PROMOTION_EMAIL_DOMAIN_NAME_IS_ALREADY_EXISTS_EXCEPTION);
    }
}
