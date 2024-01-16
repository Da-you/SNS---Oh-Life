package project.ohlife.service;


import static project.ohlife.common.utils.RandomNumberGenerator.maskEmail;
import static project.ohlife.common.utils.RandomNumberGenerator.maskPhoneNumber;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import project.ohlife.domain.user.User;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;
import project.ohlife.repository.UserRepository;
import project.ohlife.repository.dto.UserDto.LoginRequest;
import project.ohlife.repository.dto.UserDto.SignupRequest;
import project.ohlife.service.encryption.EncryptionService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;
  private final HttpSession session;
  private final EncryptionService encrypt;

  @Transactional(readOnly = true)
  public boolean checkEmailDuplicate(String email) {
    return userRepo.existsByEmail(email);
  }

  @Transactional(readOnly = true)
  public boolean checkPhoneNumberDuplicate(String phoneNumber) {
    return userRepo.existsByPhoneNumber(phoneNumber);
  }

  @Transactional
  public void signup(SignupRequest request) {
    if (checkEmailDuplicate(request.getEmail())) {
      throw new CustomException(ErrorCode.EMAIL_DUPLICATE, request.getEmail());
    }
    if (checkPhoneNumberDuplicate(request.getPhoneNumber())) {
      throw new CustomException(ErrorCode.PHONE_NUMBER_DUPLICATE, request.getPhoneNumber());
    }
    log.info("row password: {}", request.getPassword());
    String password = encrypt.encrypt(request.getPassword());
    log.info("encode password: {}", encrypt.encrypt(request.getPassword()));

    userRepo.save(request.toEntity(password));
  }

  @Transactional
  public void login(LoginRequest request) {
    existByEmailAndPassword(request);
    session.setAttribute("user", request.getEmail());
    log.info("login session: {}", session.getAttribute("user"));
  }

  @Transactional(readOnly = true)
  public void existByEmailAndPassword(LoginRequest request) {
    String encodePassword = encrypt.encrypt(request.getPassword());
    log.info("encode password: {}", encodePassword);
    if (!userRepo.existsByEmailAndPassword(request.getEmail(), encodePassword)) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }
  }

  @Transactional
  public void logout() {
    log.info("logout session: {}", session.getAttribute("user"));
    if (session.getAttribute("user") != null) {
      session.invalidate();
      log.info("logout session: {}", session.getAttribute("user"));
    }
  }

  @Transactional
  public void withdrawal(LoginRequest request) {
    existByEmailAndPassword(request);
    User user = userRepo.findByEmail(request.getEmail());

    user.withdrawal(maskEmail(user.getEmail()), maskPhoneNumber(user.getPhoneNumber()));
  }

  public User getUser(String email) {
    if (userRepo.findByEmail(email) == null) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    } else {
      return userRepo.findByEmail(email);
    }
  }
}
