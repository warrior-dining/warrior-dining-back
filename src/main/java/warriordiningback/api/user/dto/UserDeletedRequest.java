package warriordiningback.api.user.dto;

import lombok.Getter;
import warriordiningback.domain.user.User;

@Getter
public class UserDeletedRequest {

    private String email;

    private String password;

    private boolean isUsed;

    public User toEntity() {
        return new User(email, password, isUsed);
    }
}
