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
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 정보가 존재하지 않습니다. 다시 로그인해 주세요."),
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 음식점에 대한 정보가 존재하지 않습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰에 대한 정보가 존재하지 않습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 예약에 대한 정보가 존재하지 않습니다."),
    FLAG_NOT_FOUND(HttpStatus.NOT_FOUND, "가입경로를 찾을 수 없습니다. 고객센터에 문의해주세요."),

    //403,
    RESERVATION_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "음식점 주인은 본인 음식점 예약을 할 수 없습니다."),
    OWNER_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "해당 사용자에게 OWNER 권한이 없습니다."),
    SOCIAL_LOGIN_NOT_ALLOWED(HttpStatus.FORBIDDEN, "소셜 로그인 사용자는 비밀번호 찾기를 할 수 없습니다. 해당 플랫폼을 이용해주세요.");


    private final HttpStatus httpStatus;
    private final String message;
}
