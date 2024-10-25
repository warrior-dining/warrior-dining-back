package warriordiningback.api.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindPasswordResponse {

    private String newPassword;
    private boolean status;

    public static FindPasswordResponse of(String newPassword) {
        return FindPasswordResponse.builder()
                .newPassword(newPassword)
                .status(true)
                .build();
    }
}
