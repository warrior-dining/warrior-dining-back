package warriordiningback.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import warriordiningback.domain.user.User;

@Getter
public class UserEditRequest {

    private String email;

    @Pattern(regexp = "^(01[0-9]{1})[0-9]{3,4}[0-9]{4}$",
            message = "하이픈(-)을 제외한 휴대폰번호 전체를 입력하세요.")
    @NotBlank(message = "휴대폰번호를 입력하세요.")
    private String phone;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{12,}$",
            message = "비밀번호는 특수문자(@$!%*?&#), 영어 소문자를 포함한 12자리 이상 입력하세요. ")
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String newPassword;


    public User toEntity() { // Request값 toEntity화
        return new User(email, phone, password, newPassword);
    }
}
