package project.ohlife.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import project.ohlife.common.properties.CacheProperties;
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfig {

  private final CacheProperties cacheProperties;

  @Value("${spring.redis.cache.host}")
  private String host;

  @Value("${spring.redis.cache.port}")
  private int port;

  @Bean(name = "redisCacheConnectionFactory")
  public RedisConnectionFactory redisCacheConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }

  /*
   * Jackson2는 Java8의 LocalDate의 타입을 알지못해서적절하게 직렬화해주지 않는다.
   * 때문에 역직렬화 시 에러가 발생한다.
   * 따라서 적절한 ObjectMapper를 Serializer에 전달하여 직렬화 및 역직렬화를 정상화 시켰다.
   */
  private ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }

  private RedisCacheConfiguration redisCacheDefaultConfiguration() {
    return RedisCacheConfiguration
        .defaultCacheConfig()
        .serializeKeysWith(RedisSerializationContext.SerializationPair
            .fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(RedisSerializationContext.SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper())));
  }

  private Map<String, RedisCacheConfiguration> redisCacheConfigurationMap() {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    for (Entry<String, Long> cacheNameAndTimeout : cacheProperties.getTtl().entrySet()) {
      cacheConfigurations
          .put(cacheNameAndTimeout.getKey(), redisCacheDefaultConfiguration().entryTtl(
              Duration.ofSeconds(cacheNameAndTimeout.getValue())));
    }
    return cacheConfigurations;
  }

  @Bean(name = "redisCacheManager")
  public CacheManager redisCacheManager(@Qualifier("redisCacheConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
    log.info("redis cache manager init");
    return RedisCacheManager.RedisCacheManagerBuilder
        .fromConnectionFactory(redisConnectionFactory)
        .cacheDefaults(redisCacheDefaultConfiguration())
        .withInitialCacheConfigurations(redisCacheConfigurationMap()).build();
  }

}
