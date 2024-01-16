package project.ohlife.common.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import project.ohlife.domain.user.User;

@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    log.info("supportsParameter 실행");

    boolean hasCurrentUserAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
    boolean hasStringClass = parameter.getParameterType() == String.class; // 어노테이션이 붙는 파라미터 타입과 일치해야함

    // LoginUser 클래스가 이 파라미터 타입과 호환 가능한지 여부를 확인 > 호환이 가능하다는것은
    // ->  LoginUser 클래스가 파라미터 타입의 하위 클래스이거나, 또는 인터페이스를 구현하고 있는 경우를 의미합니다.

    return hasCurrentUserAnnotation && hasStringClass;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    log.info("resolveArgument 실행");

    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    HttpSession session = request.getSession(false); // 기본값 true의 경우 세션이 없으면 새로 생성합니다.
    if (session == null) {
      return null;
    }
    log.info("session: {}", session.getAttribute("user"));
    return session.getAttribute("user");
  }

}
