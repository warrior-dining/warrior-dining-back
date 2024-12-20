package warriordiningback.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "아이디를 입력하세요.")
    private String email;

    // 테스트 끝날 시 비밀번호 패턴 활성화 할 것
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{12,}$",
            message = "비밀번호는 특수문자(@$!%*?&#), 영어 소문자를 포함한 12자리 이상 입력하세요. ")
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$",
            message = "생년월일은 년(4자리)월(2자리)일(2자리) 형식으로 입력하세요.")
    @NotBlank(message = "생년월일을 입력하세요.")
    private String birth;

    // 테스트 끝날 시 휴대폰번호 패턴 활성화 할 것
    @Pattern(regexp = "^(01[0-9]{1})[0-9]{3,4}[0-9]{4}$",
            message = "하이픈(-)을 제외한 휴대폰번호 전체를 입력하세요.")
    @NotBlank(message = "휴대폰번호를 입력하세요.")
    private String phone;

    @NotNull(message = "성별을 선택하세요.")
    private Long gender;

}
