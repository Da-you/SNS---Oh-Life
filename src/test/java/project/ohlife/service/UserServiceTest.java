package project.ohlife.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.ohlife.domain.user.User;
import project.ohlife.fixture.UserFixture;
import project.ohlife.repository.UserRepository;
import project.ohlife.repository.dto.UserDto.LoginRequest;
import project.ohlife.repository.dto.UserDto.SignupRequest;

@SpringBootTest
class UserServiceTest {

  @Autowired
  private UserService userService;
  @MockBean
  private UserRepository userRepository;


  @Test
  void givenSignupRequest_whenSignup_thenSuccess() throws Exception {
    SignupRequest request = SignupRequest.builder()
        .email("abcd@naver.com")
        .password("1234")
        .nickname("abcd")
        .phoneNumber("010-1234-5678")
        .build();

    assertDoesNotThrow(() -> userService.signup(request));
  }

  @Test
  void givenLoginRequest_whenLogin_thenSuccess() throws Exception {
    LoginRequest request = LoginRequest.builder()
        .email("abcd@naver.com")
        .password("1234")
        .build();
    User fixture = UserFixture.get(request.getEmail(), request.getPassword());
    when(userRepository.findByEmailAndPassword(request.getEmail(),
        request.getPassword())).thenReturn(fixture);
    Assertions.assertDoesNotThrow(() -> userService.login(request));
  }

  // TODO: 로그인시 해댱 email로 가입한 유저가 없는경우
  // TODO: 로그인시 비밀번호가 틀린경우
}
