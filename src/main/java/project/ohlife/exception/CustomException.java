package project.ohlife.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 전역으로 사용할 exception RuntimeException을 상속받아서 Unchecked Exception으로 만들어준다. 생성자로는 enum ErrorCode를 받아서
 * errorCode를 저장한다.
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;
  private final String message;

  public CustomException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.message = null;
  }

  @Override
  public String getMessage() {
    if (message == null) {
      return errorCode.getDetail();
    } else {
      return String.format("%s. %s", errorCode.getDetail(), message);
    }

  }





}

