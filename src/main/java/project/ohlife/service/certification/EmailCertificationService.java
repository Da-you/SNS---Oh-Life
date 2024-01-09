package project.ohlife.service.certification;

import static project.ohlife.common.utils.certification.CertificationConstants.TITLE;
import static project.ohlife.common.utils.certification.CertificationNumberGenerator.generateNumber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.ohlife.common.utils.certification.CertificationImpl;
import project.ohlife.common.utils.certification.MessageTemplate;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;
import project.ohlife.repository.dto.UserDto.CertificationRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailCertificationService {

  private final JavaMailSender mailSender;
  private final CertificationImpl emailCertification;

  // 이메일 전송 및 인증번호 저장
  public void sendEmailForCertification(String email) {

    String randomNumber = generateNumber();
    String content = makeEmailContent(randomNumber);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject(TITLE);
    message.setText(content);
    try {
      mailSender.send(message);
      log.info("인증번호: {}", randomNumber);
    } catch (Exception e) {
      throw new CustomException(ErrorCode.SEND_EMAIL_FAIL);
    }
    emailCertification.createCertification(email, randomNumber);
  }

  // 인증 이메일 내용 생성
  public String makeEmailContent(String certificationNumber) {
    MessageTemplate content = new MessageTemplate();
    return content.CertificationContent(certificationNumber);
  }

  // 인증번호 일치 여부 확인
  public void verifyEmail(CertificationRequest requestDto) {
    if (isVerify(requestDto)) {
      throw new CustomException(ErrorCode.INVALID_CERTIFICATION_NUMBER);
    }
    emailCertification.deleteCertification(requestDto.getKey());

  }

  // 인증번호 일치 여부 확인 내부 로직
  // true : 인증번호 일치하지 않음
  private boolean isVerify(CertificationRequest requestDto) {
    return !(emailCertification.hasKey(requestDto.getKey()) &&
        emailCertification.getCertification(requestDto.getKey())
            .equals(requestDto.getCertificationNumber()));
  }

}
