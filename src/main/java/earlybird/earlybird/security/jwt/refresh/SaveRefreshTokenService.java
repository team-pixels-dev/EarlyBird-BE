package earlybird.earlybird.security.jwt.refresh;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class SaveRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String accountId, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshToken refreshToken = new RefreshToken(accountId, refresh, date.toString());
        refreshTokenRepository.save(refreshToken);
    }
}
