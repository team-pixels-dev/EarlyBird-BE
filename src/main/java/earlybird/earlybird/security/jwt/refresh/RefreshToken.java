package earlybird.earlybird.security.jwt.refresh;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountId;
    private String refreshToken;
    private String expiration;

    public RefreshToken() {

    }

    public RefreshToken(String accountId, String refreshToken, String expiration) {
        this.accountId = accountId;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
