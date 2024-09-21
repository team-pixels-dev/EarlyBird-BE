package earlybird.earlybird.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String accountId;
    private String name;
    private String email;
    private String role;
}
