package earlybird.earlybird.appointment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

/**
 * @deprecated Appointment 관련 클래스는 2024년 하반기 베타테스트에서 사용함
 */
@Deprecated
public interface RepeatingDayRepository extends JpaRepository<RepeatingDay, Long> {

    List<RepeatingDay> findAllByDayOfWeek(DayOfWeek dayOfWeek);
}
