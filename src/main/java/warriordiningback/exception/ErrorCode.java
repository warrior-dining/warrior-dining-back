package warriordiningback.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_ID(HttpStatus.CONFLICT, "이미 가입된 이메일이 존재합니다."), //409
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호를 확인하세요."), // 401

    // 404
    GENDER_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "성별에 대한 정보가 존재하지 않습니다."),
    ROLE_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "권한에 대한 정보가 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    INQUIRY_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 문의사항은 존재하지 않습니다."),
    CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 코드입니다."),
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 음식점에 대한 정보가 존재하지 않습니다."),

    //403
    OWNER_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "해당 사용자에게 OWNER 권한이 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
