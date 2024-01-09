package project.ohlife.common.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.ohlife.common.annotation.LoginUserArgumentResolver;
import project.ohlife.common.interceptor.LogInterceptor;
import project.ohlife.common.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new LoginUserArgumentResolver());
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheckInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/users/login", "/users/signup", "/users/mail-certification/**",
            "/users/sms-certification/**", "/users/email/{email}",
            "/users/phoneNumber/{phoneNumber}");
    registry.addInterceptor(new LogInterceptor())
        .order(2)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**", "/*.ico", "/error");
  }
}
