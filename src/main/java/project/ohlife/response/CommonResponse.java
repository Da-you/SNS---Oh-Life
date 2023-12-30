package project.ohlife.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class CommonResponse<T> {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private final Boolean isSuccess;
  private final String message;
  private T data;


  public static <T> CommonResponse<T> ok(T data) {
    return CommonResponse.<T>builder()
        .isSuccess(true)
        .message("success")
        .data(data)
        .build();
  }


}
