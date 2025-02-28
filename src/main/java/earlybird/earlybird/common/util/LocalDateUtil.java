package earlybird.earlybird.common.util;

import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateUtil {
    public static LocalDate getLocalDateNow() {
        return LocalDate.now(ZoneId.of("Asia/Seoul"));
    }
}
