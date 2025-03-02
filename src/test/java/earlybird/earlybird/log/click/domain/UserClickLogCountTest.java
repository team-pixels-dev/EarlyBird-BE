package earlybird.earlybird.log.click.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

class UserClickLogCountTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @DisplayName("UserClickLogCount 객체 생성 시 clickCount 값은 0으로 초기화된다")
    @Test
    void clickCountInitZero() {
        // given
        UserClickLogCount clickLogCount =
                UserClickLogCount.builder()
                        .clickType("clickType")
                        .clickDate(LocalDate.of(2025, 1, 1))
                        .clientId("clientId")
                        .build();

        assertThat(clickLogCount.getClickCount()).isEqualTo(0);
    }

    @DisplayName("increaseClickCount 메서드를 호출하면 clickCount 값이 1 증가한다")
    @Test
    void clickCountPlusOneWhenInvokeIncreaseClickCount() {
        UserClickLogCount clickLogCount =
                UserClickLogCount.builder()
                        .clickType("clickType")
                        .clickDate(LocalDate.of(2025, 1, 1))
                        .clientId("clientId")
                        .build();

        clickLogCount.increaseClickCount();

        assertThat(clickLogCount.getClickCount()).isEqualTo(1);
    }

    @DisplayName("clickType 이 null/빈 문자열/공백 이면 예외가 발생한다 (Not Blank)")
    @Test
    void throwExceptionWhenClickTypeIsBlank() {
        UserClickLogCount clickLogCountWithNull =
                UserClickLogCount.builder()
                        .clickDate(LocalDate.of(2025, 1, 1))
                        .clientId("clientId")
                        .build();

        UserClickLogCount clickLogCountWithEmpty =
                UserClickLogCount.builder()
                        .clickType("")
                        .clickDate(LocalDate.of(2025, 1, 1))
                        .clientId("clientId")
                        .build();

        UserClickLogCount clickLogCountWithWhitespace =
                UserClickLogCount.builder()
                        .clickType("  ")
                        .clickDate(LocalDate.of(2025, 1, 1))
                        .clientId("clientId")
                        .build();

        Set<ConstraintViolation<UserClickLogCount>> violationsWithNull =
                validator.validate(clickLogCountWithNull);
        Set<ConstraintViolation<UserClickLogCount>> violationsWithEmpty =
                validator.validate(clickLogCountWithEmpty);
        Set<ConstraintViolation<UserClickLogCount>> violationsWithWhitespace =
                validator.validate(clickLogCountWithWhitespace);

        assertThat(violationsWithNull).isNotEmpty();
        assertThat(violationsWithNull)
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("clickType"));

        assertThat(violationsWithEmpty).isNotEmpty();
        assertThat(violationsWithEmpty)
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("clickType"));

        assertThat(violationsWithWhitespace).isNotEmpty();
        assertThat(violationsWithWhitespace)
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("clickType"));
    }

    @DisplayName("clientId 가 null/빈 문자열/공백 이면 예외가 발생한다 (Not Blank)")
    @Test
    void throwExceptionWhenClientIdIsBlank() {
        UserClickLogCount clickLogCountWithNull =
                UserClickLogCount.builder()
                        .clickType("clickType")
                        .clickDate(LocalDate.of(2025, 1, 1))
                        .build();

        UserClickLogCount clickLogCountWithEmpty =
                UserClickLogCount.builder()
                        .clickType("clickType")
                        .clickDate(LocalDate.of(2025, 1, 1))
                        .clientId("")
                        .build();

        UserClickLogCount clickLogCountWithWhitespace =
                UserClickLogCount.builder()
                        .clickType("clickType")
                        .clickDate(LocalDate.of(2025, 1, 1))
                        .clientId("  ")
                        .build();

        Set<ConstraintViolation<UserClickLogCount>> violationsWithNull =
                validator.validate(clickLogCountWithNull);
        Set<ConstraintViolation<UserClickLogCount>> violationsWithEmpty =
                validator.validate(clickLogCountWithEmpty);
        Set<ConstraintViolation<UserClickLogCount>> violationsWithWhitespace =
                validator.validate(clickLogCountWithWhitespace);

        assertThat(violationsWithNull).isNotEmpty();
        assertThat(violationsWithNull)
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("clientId"));

        assertThat(violationsWithEmpty).isNotEmpty();
        assertThat(violationsWithEmpty)
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("clientId"));

        assertThat(violationsWithWhitespace).isNotEmpty();
        assertThat(violationsWithWhitespace)
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("clientId"));
    }

    @DisplayName("clickDate 가 null 이면 예외가 발생한다 (Not Null)")
    @Test
    void throwExceptionWhenClickDateIsNull() {
        UserClickLogCount clickLogCountWithNull =
                UserClickLogCount.builder().clickType("clickType").clientId("clientId").build();

        Set<ConstraintViolation<UserClickLogCount>> violationsWithNull =
                validator.validate(clickLogCountWithNull);

        assertThat(violationsWithNull).isNotEmpty();
        assertThat(violationsWithNull)
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("clickDate"));
    }
}
