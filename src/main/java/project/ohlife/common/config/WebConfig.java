package project.ohlife.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.ohlife.common.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheckInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/users/login", "/users/signup", "/users/mail-certification/**",
            "/users/sms-certification/**","/users/email/{email}", "/users/phoneNumber/{phoneNumber}");

  }
}
