package project.ohlife.service;

import static project.ohlife.domain.user.User.createUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;
import project.ohlife.repository.UserRepository;
import project.ohlife.repository.dto.UserDto.LoginRequest;
import project.ohlife.repository.dto.UserDto.SignupRequest;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;

  @Transactional
  public void signup(SignupRequest request) {
    userRepo.save(request.toEntity());
  }

  @Transactional
  public void login(LoginRequest request) {
    existsByEmail(request.getEmail());
    existsByPassword(request.getPassword());

  }

  @Transactional(readOnly = true)
  public void existsByPassword(String password) {
    if (userRepo.existsByPassword(password)) {
      throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
    }
  }

  @Transactional(readOnly = true)
  public void existsByEmail(String email) {
    if (userRepo.existsByEmail(email)) {
      throw new CustomException(ErrorCode.NOT_FOUND_USER);
    }
  }
}
