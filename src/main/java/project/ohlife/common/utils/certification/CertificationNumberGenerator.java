package project.ohlife.common.utils.certification;

import java.security.SecureRandom;

public class CertificationNumberGenerator {
// 6자리의 랜덤한 인증번호 생성 (100000 ~ 999999)
  public static final String generateNumber() {
    SecureRandom random = new SecureRandom();
    return String.valueOf(random.nextInt(900000) + 100000);
  }

}
