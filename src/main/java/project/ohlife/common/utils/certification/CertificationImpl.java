package project.ohlife.common.utils.certification;

import static project.ohlife.common.utils.certification.CertificationConstants.LIMIT_TIME_CERTIFICATION_NUMBER;
import static project.ohlife.common.utils.certification.CertificationConstants.PREFIX_CERTIFICATION;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationImpl implements Certification{

  private final StringRedisTemplate redisTemplate;

  @Override
  public void createCertification(String key, String certificationNumber) {
    redisTemplate.opsForValue().set(PREFIX_CERTIFICATION + key,
        certificationNumber,
        Duration.ofSeconds(LIMIT_TIME_CERTIFICATION_NUMBER));
  }

  @Override
  public String getCertification(String key) {
    return redisTemplate.opsForValue().get(PREFIX_CERTIFICATION + key);
  }

  @Override
  public void deleteCertification(String key) {
    redisTemplate.delete(PREFIX_CERTIFICATION + key);

  }

  @Override
  public boolean hasKey(String key) {
    return redisTemplate.hasKey(PREFIX_CERTIFICATION + key);
  }
}
