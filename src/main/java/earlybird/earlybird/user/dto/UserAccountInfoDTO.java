package earlybird.earlybird.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserAccountInfoDTO {
    private Long id;
    private String accountId;
    private String name;
    private String email;
    private String role;
}
