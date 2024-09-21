package earlybird.earlybird.security.jwt.refresh;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByRefreshToken(String refresh);
}
