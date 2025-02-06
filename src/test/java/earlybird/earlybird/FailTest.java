package earlybird.earlybird;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FailTest {

    @Test
    public void test() {
        Assertions.assertThat(true).isFalse();
    }
}
