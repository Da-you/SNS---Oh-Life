package project.ohlife.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ohlife.repository.dto.UserDto.LoginRequest;
import project.ohlife.repository.dto.UserDto.SignupRequest;
import project.ohlife.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public void signup(SignupRequest request) {
    userService.signup(request);
  }

  @PostMapping("/login")
  public void login(LoginRequest request) {
    userService.login(request);
  }

}
