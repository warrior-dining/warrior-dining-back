package warriordiningback.api.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import warriordiningback.domain.user.Role;
import warriordiningback.domain.user.User;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private boolean isUsed;
    private Set<Role> roles;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private boolean status;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .isUsed(user.isUsed())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .status(true)
                .build();
    }
}
