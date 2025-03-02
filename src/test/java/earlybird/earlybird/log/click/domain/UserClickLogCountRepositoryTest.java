package earlybird.earlybird.log.click.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
class UserClickLogCountRepositoryTest {
    @Autowired private UserClickLogCountRepository repository;

    @Autowired private EntityManager entityManager;

    @DisplayName("clientId, clickType, clickDate 조합으로 UserClickLogCount 객체를 조회한다")
    @Test
    void test() {
        String clickType = "CLICK_A";
        String clientId = "client123";
        LocalDate clickDate = LocalDate.of(2024, 3, 1);
        UserClickLogCount logCount =
                UserClickLogCount.builder()
                        .clickType(clickType)
                        .clientId(clientId)
                        .clickDate(clickDate)
                        .build();

        repository.save(logCount);
        entityManager.flush();

        Optional<UserClickLogCount> optionalClickCount =
                repository.findByClientIdAndClickTypeAndClickDate(clientId, clickType, clickDate);
        assertThat(optionalClickCount).isPresent();

        UserClickLogCount clickCount = optionalClickCount.get();
        assertThat(clickCount.getClickDate()).isEqualTo(clickDate);
        assertThat(clickCount.getClickType()).isEqualTo(clickType);
        assertThat(clickCount.getClientId()).isEqualTo(clientId);
    }
}
