package earlybird.earlybird.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.logging.LogLevel.*;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class LogUtilTest {

    @Autowired
    private LoggingSystem loggingSystem;

    @DisplayName("로그 메시지가 정상적으로 출력된다")
    @Test
    void logMessage(CapturedOutput output) {
        String message = "test message";
        LogUtil.log(INFO, Map.of(), message);
        assertThat(output.getOut()).contains(message);
    }

    @DisplayName("변수 값이 포함된 로그 메시지가 정상적으로 출력된다")
    @Test
    void varInLogMessage(CapturedOutput output) {
        String message = "message1={}";
        String var1 = "var1";
        String expected = "message1=var1";

        LogUtil.log(INFO, Map.of(), message, var1);
        assertThat(output.getOut()).contains(expected);
    }

    @DisplayName("여러 개의 변수 값이 포함된 로그 메시지가 정상적으로 출력된다")
    @Test
    void manyVarInLogMessage(CapturedOutput output) {
        String message = "message1={}, message2={}, message3={}";
        String var1 = "var1";
        String var2 = "var2";
        String var3 = "var3";
        String expected = "message1=var1, message2=var2, message3=var3";

        LogUtil.log(INFO, Map.of(), message, var1, var2, var3);
        assertThat(output.getOut()).contains(expected);
    }

    @DisplayName("매개변수로 설정한 INFO 레벨로 로그가 출력된다")
    @Test
    void infoLogLevel(CapturedOutput output) {
        LogUtil.log(INFO, Map.of(), "");
        assertThat(output.getOut()).contains("\"level\":\"INFO\"");
    }

    @DisplayName("매개변수로 설정한 WARN 레벨로 로그가 출력된다")
    @Test
    void infoWarnLevel(CapturedOutput output) {
        LogUtil.log(WARN, Map.of(), "");
        assertThat(output.getOut()).contains("\"level\":\"WARN\"");
    }

    @DisplayName("매개변수로 설정한 ERROR 레벨로 로그가 출력된다")
    @Test
    void infoErrorLevel(CapturedOutput output) {
        LogUtil.log(ERROR, Map.of(), "");
        assertThat(output.getOut()).contains("\"level\":\"ERROR\"");
    }

    @DisplayName("매개변수로 설정한 DEBUG 레벨로 로그가 출력된다")
    @Test
    void infoDebugLevel(CapturedOutput output) {
        loggingSystem.setLogLevel("root", DEBUG);
        LogUtil.log(DEBUG, Map.of(), "");
        assertThat(output.getOut()).contains("\"level\":\"DEBUG\"");
        loggingSystem.setLogLevel("root", INFO);
    }

    @DisplayName("매개변수로 설정한 TRACE 레벨로 로그가 출력된다")
    @Test
    void infoTraceLevel(CapturedOutput output) {
        loggingSystem.setLogLevel("root", TRACE);
        LogUtil.log(TRACE, Map.of(), "");
        assertThat(output.getOut()).contains("\"level\":\"TRACE\"");
        loggingSystem.setLogLevel("root", INFO);
    }

    @DisplayName("매개변수로 설정한 MDC 프로퍼티 값이 로그에 출력된다")
    @Test
    void mdcProperties(CapturedOutput output) {
        LogUtil.log(INFO, Map.of("key1", "val1"), "");
        assertThat(output.getOut()).contains("\"key1\":\"val1\"");
    }
}