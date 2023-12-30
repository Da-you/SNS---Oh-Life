package project.ohlife.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.ohlife.response.ErrorResponse;

/**
 * 전역에서 발생하는 모든 에러를 잡기위한 class
 * defaultException 은 확인되지 않는 에러를 잡기위한 것
 * customException 은 개발자가 정의한 에러를 잡기위한 것
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> defaultException(Exception e) {
    log.error(String.valueOf(e));
    e.printStackTrace();
    ErrorResponse response = new ErrorResponse(false,"UNKNOWN_ERROR", "50001", "unknown error");
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<ErrorResponse> customException(CustomException e) {
    log.error("custom exception : {} ",e.getMessage());
    return ErrorResponse.toEntity(e.getErrorCode());
  }

}
