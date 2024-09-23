package earlybird.earlybird.security.jwt.access;

import earlybird.earlybird.security.jwt.JWTUtil;
import earlybird.earlybird.user.dto.UserAccountInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateAccessTokenService {

    private final JWTUtil jwtUtil;

    public String createAccessToken(UserAccountInfoDTO userDTO, final Long expiredMs) {
        String accountId = userDTO.getAccountId();
        String role = userDTO.getRole();

        return jwtUtil.createJwt("access", accountId, role, expiredMs);
    }
}
