package project.ohlife.service.certification;

import static project.ohlife.common.utils.RandomNumberGenerator.generateNumber;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.MessageType;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.ohlife.common.properties.SmsAppProperties;
import project.ohlife.common.utils.certification.CertificationImpl;
import project.ohlife.common.utils.certification.MessageTemplate;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;
import project.ohlife.repository.dto.UserDto.CertificationRequest;

@Slf4j
@Service
public class SmsCertificationService {

  private final CertificationImpl smsCertification;
  private final SmsAppProperties smsAppProperties;
  private final DefaultMessageService messageService;

  @Autowired
  public SmsCertificationService(CertificationImpl smsCertification, SmsAppProperties smsAppProperties
    ) {
    this.smsCertification = smsCertification;
    this.smsAppProperties = smsAppProperties;
    this.messageService = NurigoApp.INSTANCE.initialize(
        smsAppProperties.getApi(), smsAppProperties.getSecret(), "https://api.coolsms.co.kr");

  }

  public void sendSms(CertificationRequest request) {

    String certificationCode = generateNumber();

    Message sms = new Message();
    sms.setFrom(smsAppProperties.getFrom());
    sms.setTo(request.getKey());
    sms.setType(MessageType.SMS);
    sms.setText(makeSmsContent(certificationCode));

    try {
      messageService.sendOne(new SingleMessageSendingRequest(sms));
    } catch (Exception e) {
      log.error("문자 전송 실패");
    }
    smsCertification.createCertification(request.getKey(), certificationCode);
    log.info("인증번호: {}", certificationCode);
  }

  public String makeSmsContent(String certificationNumber) {
    MessageTemplate content = new MessageTemplate();
    return content.CertificationContent(certificationNumber);
  }

  // 입력한 인증번호가 발송되었던(세션에 저장된) 인증번호가 동일한지 확인
  // 인증번호 일치 여부 확인
  public void verifySms(CertificationRequest requestDto) {
    if (isVerify(requestDto)) {
      throw new CustomException(ErrorCode.INVALID_CERTIFICATION_NUMBER);
    }
    smsCertification.deleteCertification(requestDto.getKey());

  }

  // 인증번호 일치 여부 확인 내부 로직
  // true : 인증번호 일치하지 않음
  private boolean isVerify(CertificationRequest requestDto) {
    return !(smsCertification.hasKey(requestDto.getKey()) &&
        smsCertification.getCertification(requestDto.getKey())
            .equals(requestDto.getCertificationNumber()));
  }
}
