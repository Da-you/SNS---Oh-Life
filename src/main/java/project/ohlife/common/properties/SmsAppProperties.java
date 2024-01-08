package project.ohlife.common.properties;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("certification-sms")
public class SmsAppProperties {

  private final String api;
  private final String secret;
  private final String from;


}
