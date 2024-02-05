package project.ohlife.common.properties;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("certification-sms")
public class CacheProperties {

  private final Map<String, Long> ttl = new HashMap<>();


}
