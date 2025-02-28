package earlybird.earlybird.common.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.logging.LogLevel;

import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
public class LogUtil {

    /**
     *
     * @param attributes 로그에 MDC 필드에 추가할 정보
     * @param message 로그 메시지
     * @param messageArgs 로그 메시지에 들어가는 변수
     */
    public static void log(LogLevel logLevel, Map<String, String> attributes, String message, Object... messageArgs) {
        attributes.forEach(MDC::put);
        getLogMethod(logLevel).accept(message, messageArgs);
        attributes.forEach((key, value) -> MDC.remove(key));
    }

    private static BiConsumer<String, Object[]> getLogMethod(LogLevel logLevel) {
        return switch (logLevel) {
            case TRACE -> log::trace;
            case DEBUG -> log::debug;
            case INFO -> log::info;
            case WARN -> log::warn;
            case ERROR -> log::error;
            default -> log::info;
        };
    }
}
