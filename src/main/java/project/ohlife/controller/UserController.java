package project.ohlife.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.ohlife.common.annotation.LoginUser;
import project.ohlife.domain.user.User;
import project.ohlife.repository.dto.UserDto.CertificationRequest;
import project.ohlife.repository.dto.UserDto.LoginRequest;
import project.ohlife.repository.dto.UserDto.ProfileResponse;
import project.ohlife.repository.dto.UserDto.ProfileUpdateRequest;
import project.ohlife.repository.dto.UserDto.SearchUserResponse;
import project.ohlife.repository.dto.UserDto.SignupRequest;
import project.ohlife.response.CommonResponse;
import project.ohlife.response.PageResponse;
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

  @GetMapping("/email/{email}")
  public CommonResponse<Boolean> checkEmailDuplicate(@PathVariable String email) {
    return CommonResponse.ok(userService.checkEmailDuplicate(email));
  }

  @GetMapping("/phoneNumber/{phoneNumber}")
  public CommonResponse<Boolean> checkPhoneNumberDuplicate(@PathVariable String phoneNumber) {
    return CommonResponse.ok(userService.checkPhoneNumberDuplicate(phoneNumber));
  }

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

  @DeleteMapping("/logout")
  public CommonResponse<Void> logout(@LoginUser String email) {
    userService.logout();
    return CommonResponse.ok();
  }

  @PostMapping("/withdrawal")
  public CommonResponse<Void> withdrawal(@LoginUser String email,
      @RequestBody @Valid LoginRequest request) {
    userService.withdrawal(request);
    return CommonResponse.ok();
  }

  @GetMapping("/search")
  public CommonResponse<PageResponse<SearchUserResponse>> searchUser(@LoginUser String email,
      @RequestParam String keyword) {
    return CommonResponse.ok(userService.searchUser(email, keyword));
  }
  @GetMapping("/my-profile")
  public CommonResponse<ProfileResponse> getMyProfile(@LoginUser String email) {
    return CommonResponse.ok(userService.myProfile(email));
  }

  @GetMapping("/profile/{userId}")
  public CommonResponse<ProfileResponse> getUserProfile(@LoginUser String email,@PathVariable Long userId) {
    return CommonResponse.ok(userService.getUserProfile(email, userId));
  }

  @PatchMapping("/profile-image")
  public CommonResponse<Void> updateProfileImage(@LoginUser String email,
      @RequestPart(required = false,name = "image") MultipartFile profileImage) {
    userService.updateProfileImage(email, profileImage);
    return CommonResponse.ok();
  }

  @PatchMapping("/profile")
  public CommonResponse<Void> updateProfile(@LoginUser String email,
      @RequestBody ProfileUpdateRequest request) {
    userService.updateProfile(email, request);
    return CommonResponse.ok();
  }

}
