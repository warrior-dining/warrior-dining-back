package warriordiningback.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String birth;

    @NotBlank
    private String phone;
}
