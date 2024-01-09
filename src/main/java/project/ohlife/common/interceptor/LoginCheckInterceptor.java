package project.ohlife.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String requestURI = request.getRequestURI();
    log.info("login check interceptor 실행 {}", requestURI);

    HttpSession session = request.getSession();
    if (session == null || session.getAttribute("user") == null) {
      log.info("비 로그인 유저");
      throw new CustomException(ErrorCode.UNAUTHENTICATED_USER);
    }

    return true;
  }
}
