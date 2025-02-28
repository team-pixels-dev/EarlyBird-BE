package earlybird.earlybird.log.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Map;

@Slf4j
public class LogUtil {

    /**
     *
     * @param attributes 로그에 MDC 필드에 추가할 정보
     * @param message 로그 메시지
     * @param messageArgs 로그 메시지에 들어가는 변수
     */
    public static void infoLog(Map<String, String> attributes, String message, Object... messageArgs) {
        attributes.forEach(MDC::put);
        log.info(message, messageArgs);
        attributes.forEach((key, value) -> MDC.remove(key));
    }
}
