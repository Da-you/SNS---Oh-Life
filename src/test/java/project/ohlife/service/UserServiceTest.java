package project.ohlife.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.ohlife.domain.user.User;
import project.ohlife.repository.UserRepository;
import project.ohlife.repository.dto.UserDto.SignupRequest;

@SpringBootTest
public class UserServiceTest {

  @Autowired
  private UserService userService;
  @Autowired
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
}
