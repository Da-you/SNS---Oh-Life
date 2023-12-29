package project.ohlife.domain.user;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.ohlife.domain.user.common.UserBase;
import project.ohlife.domain.user.common.UserRole;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User extends UserBase {

  private String nickname;

  private String profileImage;

  private String description;

  private String phoneNumber;

  private Boolean isVerified; // 이메일 인증 및 휴대폰 인증 여부 저장

  public User(Long id, String email, String password, UserRole role) {
    super(id, email, password, role);
  }
}
