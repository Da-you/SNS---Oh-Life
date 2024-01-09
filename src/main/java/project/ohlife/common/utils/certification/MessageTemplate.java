package project.ohlife.common.utils.certification;

public class MessageTemplate {
  public String CertificationContent(String certificationNumber) {

    StringBuilder builder = new StringBuilder();
    builder.append("oh, life 서비스 회원가입 인증번호는 ");
    builder.append(certificationNumber);
    builder.append("입니다. ");

    return builder.toString();
  }

}
