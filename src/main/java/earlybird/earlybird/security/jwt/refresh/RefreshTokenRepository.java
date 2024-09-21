package earlybird.earlybird.security.jwt.refresh;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    void deleteByRefresh(String refresh);
}
