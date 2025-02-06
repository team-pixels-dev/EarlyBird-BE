package earlybird.earlybird;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FailTest {
    @Test
    void failTest() {
        Assertions.assertThat(false).isTrue();
    }

}
