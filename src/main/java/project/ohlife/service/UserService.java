package project.ohlife.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ohlife.repository.UserRepository;
import project.ohlife.repository.dto.UserDto.LoginRequest;
import project.ohlife.repository.dto.UserDto.SignupRequest;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;
  public void signup(SignupRequest request) {

  }


  public void login(LoginRequest request) {

  }
}
