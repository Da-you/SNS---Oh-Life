package project.ohlife.common.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Component
@RequiredArgsConstructor
public class EmailAppProperties {

  //TODO : @Value 가 아닌 @ConfigurationProperties로 변경하기

  @Value("${spring.mail.host}")
  public String host;
  @Value("${spring.mail.port}")
  public int port;
  @Value("${spring.mail.username}")
  public String username;
  @Value("${spring.mail.password}")
  public String password;

}
