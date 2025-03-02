package earlybird.earlybird.log.click.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserClickLogCountJpaTest {
    @Autowired
    private UserClickLogCountRepository repository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("제약 조건을 만족하는 UserClickLogCount 는 정상적으로 저장된다")
    @Test
    void saveValidUserClickLogCount() {
        UserClickLogCount logCount = UserClickLogCount.builder()
                .clickType("CLICK_A")
                .clientId("client123")
                .clickDate(LocalDate.of(2024, 3, 1))
                .build();

        UserClickLogCount savedLog = repository.save(logCount);
        entityManager.flush();

        Optional<UserClickLogCount> retrievedLog = repository.findById(savedLog.getId());
        assertThat(retrievedLog).isPresent();
        assertThat(retrievedLog.get().getClickType()).isEqualTo("CLICK_A");
        assertThat(retrievedLog.get().getClientId()).isEqualTo("client123");
        assertThat(retrievedLog.get().getClickDate()).isEqualTo(LocalDate.of(2024, 3, 1));
    }


    @Test
    @DisplayName("같은 clickType, clientId, clickDate 조합이 있으면 DB 제약 조건 위반 예외가 발생한다")
    void testUniqueConstraintViolation() {
        UserClickLogCount logCount1 = UserClickLogCount.builder()
                .clickType("CLICK_A")
                .clientId("client123")
                .clickDate(LocalDate.of(2024, 3, 1))
                .build();
        repository.save(logCount1);
        entityManager.flush();

        UserClickLogCount logCount2 = UserClickLogCount.builder()
                .clickType("CLICK_A")
                .clientId("client123")
                .clickDate(LocalDate.of(2024, 3, 1)) // 동일한 값
                .build();

        assertThatThrownBy(() -> {
            repository.save(logCount2);
            entityManager.flush();
        })
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasCauseInstanceOf(PersistenceException.class);
    }

}