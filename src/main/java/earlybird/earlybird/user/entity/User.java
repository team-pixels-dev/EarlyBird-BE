package earlybird.earlybird.user.entity;

import earlybird.earlybird.security.authentication.oauth2.dto.OAuth2ServerResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 스프링 시큐리티에서 사용하는 값
     */
    @Column(unique = true)
    private String accountId;
    private String name;
    private String email;
    private String role;

    public void fromOAuth2ServerResponse(OAuth2ServerResponse userInfo) {
        this.accountId = userInfo.getProviderName() + " " + userInfo.getProviderId();
        this.name = userInfo.getName();
        this.email = userInfo.getEmail();
        this.role = "USER";
    }
}
