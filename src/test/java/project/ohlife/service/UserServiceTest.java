package project.ohlife.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.ohlife.domain.user.User;
import project.ohlife.domain.user.common.UserRole;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;
import project.ohlife.repository.UserRepository;
import project.ohlife.repository.dto.UserDto;
import project.ohlife.repository.dto.UserDto.LoginRequest;
import project.ohlife.repository.dto.UserDto.SignupRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;


  private UserDto.SignupRequest setRequest() {
    SignupRequest request = SignupRequest.builder()
        .email("abcd@naver.com")
        .password("kfcayo123")
        .nickname("abcd")
        .phoneNumber("01012345678")
        .build();
    return request;
  }

  private LoginRequest setLoginRequest() {
    LoginRequest request = LoginRequest.builder()
        .email("abcd@naver.com")
        .password("kfcayo123")
        .build();
    return request;
  }

  @BeforeEach
  public void testUser() {
     User user = User.createUser("abcd@naver.com", "kfcayo123", UserRole.USER, "abcd", "01012345678");
     userRepository.save(user);
  }

  @AfterEach
  public void deleteAll() {
    userRepository.deleteAll();
  }


  @Test
  @DisplayName("[회원가입]- 회원가입 성공")
  void givenSignupRequest_whenSignup_thenSuccess() {
    assertDoesNotThrow(() -> userService.signup(setRequest()));
  }

  @Test
  @DisplayName("[회원가입]- 회원가입 실패(이메일 중복검사 실패)")
  void givenSignupRequest_whenSignup_thenFail() {
    SignupRequest request = SignupRequest.builder()
        .email("abcd@naver.com")
        .password("kfcayo123")
        .nickname("abcd")
        .phoneNumber("01012345678")
        .build();
    when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
    assertThrows(CustomException.class, () -> userService.signup(request));
    verify(userRepository, atLeastOnce()).existsByEmail(request.getEmail());

  }

  @Test
  @DisplayName("[로그인]- 로그인 성공")
  void givenLoginRequest_whenLogin_thenSuccess() {
    LoginRequest request = setLoginRequest();
    when(userRepository.existsByEmailAndPassword(request.getEmail(), request.getPassword())).thenReturn(true);
    userService.login(request);
    verify(userRepository, atLeastOnce()).
        existsByEmailAndPassword(request.getEmail(),
        request.getPassword());
  }

  // TODO: 로그인시 해댱 email로 가입한 유저가 없는경우
  @Test
  @DisplayName("[로그인]- 로그인 실패(해당 email로 가입한 유저가 없는경우)")
  void givenLoginRequest_whenLogin_thenFail() {
    LoginRequest request = LoginRequest.builder()
        .email("abcd5@naver.com")
        .password("kfcayo123")
        .build();
    when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
    CustomException customException = assertThrows(CustomException.class,
        () -> userService.login(request));
    assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_USER);
    verify(userRepository, atLeastOnce()).existsByEmail(request.getEmail());
  }
  // TODO: 로그인시 비밀번호가 틀린경우
}