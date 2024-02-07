package project.ohlife.service;


import static project.ohlife.common.utils.RandomNumberGenerator.maskEmail;
import static project.ohlife.common.utils.RandomNumberGenerator.maskPhoneNumber;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.ohlife.domain.user.User;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;
import project.ohlife.repository.UserRepository;
import project.ohlife.repository.dto.UserDto.LoginRequest;
import project.ohlife.repository.dto.UserDto.ProfileResponse;
import project.ohlife.repository.dto.UserDto.ProfileUpdateRequest;
import project.ohlife.repository.dto.UserDto.SearchUserResponse;
import project.ohlife.repository.dto.UserDto.SignupRequest;
import project.ohlife.response.PageResponse;
import project.ohlife.service.encryption.EncryptionService;
import project.ohlife.service.s3.AwsS3Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;
  private final HttpSession session;
  private final EncryptionService encrypt;
  private final AwsS3Service s3Service;

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
    log.info("expire time: {}", session.getMaxInactiveInterval());
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

  @Transactional(readOnly = true)
  public User getUser(String email) {
    if (userRepo.findByEmail(email) == null) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    } else {
      return userRepo.findByEmail(email);
    }
  }

  @Transactional
  public void updateProfileImage(String email, MultipartFile imageFile) {
    User user = getUser(email);
    if (imageFile == null) {
      s3Service.deleteImage(user.getProfileImage());
    }
    String profileImage = s3Service.uploadImage(imageFile);
    user.updateProfileImage(profileImage);
  }

  @Transactional
  public void updateProfile(String  email, ProfileUpdateRequest request) {
    User user = getUser(email);
    if (request.getNickname().equals(user.getNickname())) {
      throw new CustomException(ErrorCode.PROFILE_UPDATE_NOTHING);
    }
    user.updateProfile(request.getNickname(), request.getDescription());
  }

  @Transactional(readOnly = true)
  public PageResponse<SearchUserResponse> searchUser(String email, String keyword) {
    Page<User> users = userRepo.getUserListFindByKeyword(keyword, PageRequest.of(0, 10));

    List<SearchUserResponse> contents = users.stream()
        .map(user -> new SearchUserResponse(user.getId(), user.getNickname()))
        .toList();
    return PageResponse.of(users, contents);
  }


  @Transactional(readOnly = true)
  public ProfileResponse myProfile(String email) {
    User user = getUser(email);
    return new ProfileResponse(user.getNickname(), user.getProfileImage(), user.getDescription());
  }

  @Transactional(readOnly = true)
  public ProfileResponse getUserProfile(String email, Long userId) {
    User targetUser = userRepo.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    return new ProfileResponse(targetUser.getNickname(), targetUser.getProfileImage(),
        targetUser.getDescription());
  }


}
