package project.ohlife.common.utils;

import java.security.SecureRandom;

public class RandomNumberGenerator {
// 6자리의 랜덤한 인증번호 생성 (100000 ~ 999999)
  public static final String generateNumber() {
    SecureRandom random = new SecureRandom();
    return String.valueOf(random.nextInt(900000) + 100000);
  }

  public static final String maskPhoneNumber(String phoneNumber) {
    return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7);
  }

  public static final String maskEmail(String email) {
    int atIndex = email.indexOf("@");
    if (atIndex <= 0) {
      // '@' 기호가 없거나 첫 번째 위치에 있을 경우 처리
      return email;
    }
    return email.substring(0, 3) + "****" + email.substring(atIndex);
  }



}
