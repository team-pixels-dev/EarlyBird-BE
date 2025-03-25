package earlybird.earlybird.common.util;

import java.util.List;

public class TestClientIdCheckUtil {

    private static final List<String> TEST_CLIENT_IDS = List.of(
            "test-id"
    );

    public static boolean isTestClientId(String clientId) {
        return TEST_CLIENT_IDS.contains(clientId);
    }
}
