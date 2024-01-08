package project.ohlife.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ohlife.repository.dto.UserDto.CertificationRequest;
import project.ohlife.repository.dto.UserDto.LoginRequest;
import project.ohlife.repository.dto.UserDto.SignupRequest;
import project.ohlife.response.CommonResponse;
import project.ohlife.service.UserService;
import project.ohlife.service.certification.EmailCertificationService;
import project.ohlife.service.certification.SmsCertificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final EmailCertificationService emailCertificationService;
  private final SmsCertificationService smsCertificationService;

  @PostMapping("/signup")
  public CommonResponse<Void> signup(@RequestBody @Valid SignupRequest request) {
    userService.signup(request);
    return CommonResponse.ok();
  }

  @PostMapping("/login")
  public CommonResponse<Void> login(@RequestBody @Valid LoginRequest request) {
    userService.login(request);
    return CommonResponse.ok();
  }

  @PostMapping("/mail-certification/send")
  public CommonResponse<Void> sendEmailForCertification(@RequestBody CertificationRequest request) {
    emailCertificationService.sendEmailForCertification(request.getKey());
    return CommonResponse.ok();
  }
  @PostMapping("/mail-certification/verify")
  public CommonResponse<Void> verifyEmail(@RequestBody CertificationRequest request) {
    emailCertificationService.verifyEmail(request);
    return CommonResponse.ok();
  }

  @PostMapping("/sms-certification/send")
  public CommonResponse<Void> sendSmsForCertification(@RequestBody CertificationRequest request) {
    smsCertificationService.sendSms(request);
    return CommonResponse.ok();
  }
  @PostMapping("/sms-certification/verify")
  public CommonResponse<Void> verifySms(@RequestBody CertificationRequest request) {
    smsCertificationService.verifySms(request);
    return CommonResponse.ok();
  }

}
