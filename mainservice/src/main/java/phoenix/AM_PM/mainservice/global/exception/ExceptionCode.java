package phoenix.AM_PM.mainservice.global.exception;
import lombok.Getter;

public enum ExceptionCode {

    UNAUTHORIZED(401, "미인증 사용자 입니다."),
    NO_PERMISSION(401, "사용자 권한이 없습니다."),
    USER_NOT_FOUND(404,"사용자가 없습니다."),
    USER_EXIST(500,"사용자가 이미 있습니다."),
    CHAT_NOT_FOUND(404,"채팅이 없습니다"),
    PROJECT_NOT_FOUND(404,"프로젝트가 없습니다.");

    @Getter
    private  int status;

    @Getter
    private  String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }
}