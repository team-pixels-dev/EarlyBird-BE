package earlybird.earlybird.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestClientIdCheckUtilTest {

    @DisplayName("테스트 클라이언트 ID 값을 넣으면 true 가 반환된다.")
    @Test
    void returnTrueWithTestId() {
        assertThat(TestClientIdCheckUtil.isTestClientId("test-id")).isTrue();
    }

    @DisplayName("테스트가 아닌 클라이언트 ID 값을 넣으면 false 가 반환된다.")
    @Test
    void returnFalseWithNotTestId() {
        assertThat(TestClientIdCheckUtil.isTestClientId("client-id")).isFalse();
    }

}