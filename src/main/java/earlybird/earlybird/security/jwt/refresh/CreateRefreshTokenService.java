package earlybird.earlybird.security.jwt.refresh;

import earlybird.earlybird.security.jwt.JWTUtil;
import earlybird.earlybird.user.dto.UserAccountInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateRefreshTokenService {

    private final JWTUtil jwtUtil;
    private final SaveRefreshTokenService saveRefreshTokenService;

    public String createRefreshToken(UserAccountInfoDTO userDTO, final Long expiredMs) {
        String accountId = userDTO.getAccountId();
        String role = userDTO.getRole();

        String refresh = jwtUtil.createJwt("refresh", accountId, role, expiredMs);

        saveRefreshTokenService.saveRefreshToken(accountId, refresh, expiredMs);

        return refresh;
    }
}
