package project.ohlife.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import project.ohlife.exception.ErrorCode;


/**
 * 에러를 보낼 응답 format을 정의하는 class
 */
@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private final Boolean isSuccess;
  private final String codeName;
  private final String code;
  private final String message;

  public static ResponseEntity<ErrorResponse> toEntity(ErrorCode errorCode) {
    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(ErrorResponse.builder()
            .isSuccess(false)
            .codeName(errorCode.name())
            .code(errorCode.getCode().toString().substring(0, 3))
            .message(errorCode.getDetail())
            .build());
  }

}
