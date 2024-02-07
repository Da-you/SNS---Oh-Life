package project.ohlife.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
@Configuration
@EnableRedisHttpSession
public class RedisConfig {

  @Value("${spring.redis.session.host}")
  private String host;
  @Value("${spring.redis.session.port}")
  private int port;


  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {

    return new LettuceConnectionFactory(host, port);
  }

  @Bean(name = "redisTemplate")
  public StringRedisTemplate redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {

    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(
        new StringRedisSerializer());   //redis 모니터링시 직렬화 시퀸스데이터가 아닌  Key: String 형태로 반환
    template.setValueSerializer(new StringRedisSerializer());  // Value: String
    return template;
  }
}
