package project.ohlife.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 에러 형식을 정의하는 enum
 * HttpStatus와 ErrorCode, detail을 저장한다.
 * 개발자가 정의한 새로운 exception을 한곳에서 관리하고 재사용이 가능하다는 장점
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  // CONFLICT(409, Resource 의 현재 상태와 충돌함, 보통 중복된 데이터가 존재 )
  DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, 40901, "duplicate email"),

  // BAD_REQUEST(400, 잘못된 요청)
  INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, 40001, "invalid password"),
  INVALID_CERTIFICATION_NUMBER(HttpStatus.BAD_REQUEST, 40002, "invalid certification number"),



  // NOT_FOUND(404, Resource 를 찾을 수 없음)
  NOT_FOUND_USER(HttpStatus.NOT_FOUND, 40401, "user not found by email"),


  // 500(INTERNAL_SERVER_ERROR, 서버 내부 에러)
  SEND_EMAIL_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,50001 ,"mail send fail" );

  private final HttpStatus httpStatus;
  private final Integer code;
  private final String detail;


}
