package earlybird.earlybird.email.address.check;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CheckEmailAddressServiceTest {

    @DisplayName("올바른 이메일 형식이 주어지면 true를 반환한다.")
    @Test
    void checkValidEmail() {
        // given
        List<String> validEmails = List.of(
                "test1@naver.com",
                "124test@gmail.com",
                "test@inha.edu",
                "te4535st@hanmail.net"
        );

        CheckEmailAddressService service = new CheckEmailAddressService();

        // when
        List<Boolean> results = validEmails.stream()
                .map(service::checkEmailRegex)
                .toList();

        // then
        assertThat(results).containsOnly(true);
    }

    @DisplayName("올바르지 않은 이메일 형식이 주어지면 false를 반환한다.")
    @Test
    void checkInvalidEmail() {
        // given
        List<String> invalidEmails = List.of(
                "@naver.com",
                "testnaver.com",
                "test@gmail",
                "test@inha.e",
                "abcde"
        );

        CheckEmailAddressService service = new CheckEmailAddressService();

        // when
        List<Boolean> results = invalidEmails.stream()
                .map(service::checkEmailRegex)
                .toList();

        // then
        assertThat(results).containsOnly(false);
    }
}