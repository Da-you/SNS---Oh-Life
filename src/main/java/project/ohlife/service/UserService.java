package project.ohlife.service;

import static project.ohlife.domain.user.User.createUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ohlife.domain.user.common.UserRole;
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
    if (userRepo.existsByEmail(request.getEmail())) {
      throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
    }
    userRepo.save(createUser(request.getEmail(), request.getPassword(),
        UserRole.USER, request.getNickname(),request.getPhoneNumber()));
  }

  @Transactional
  public void login(LoginRequest request) {
    userRepo.findByEmailAndPassword(request.getEmail(), request.getPassword());

  }
}
