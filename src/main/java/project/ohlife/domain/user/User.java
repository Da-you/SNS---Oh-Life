package project.ohlife.domain.user;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import project.ohlife.domain.user.common.UserRole;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "users")
@DiscriminatorValue("users")
@NoArgsConstructor(access = PROTECTED)
public class User extends UserBase {

  private String nickname;

  private String profileImage;

  private String description;
  @Column(unique = true)
  private String phoneNumber;

  private Boolean isVerified; // 이메일 인증 및 휴대폰 인증 여부 저장
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;


//  public static User createUser(String email, String password, UserRole role, String nickname,
//      String phoneNumber) {
//    User user = new User();
//    user.email = email;
//    user.password = password;
//    user.role = role;
//    user.nickname = nickname;
//    user.phoneNumber = phoneNumber;
//    user.isVerified = true;
//    return user;
//  }

  @Builder
  public User(Long id, String email, String password, UserRole role, String nickname, String phoneNumber) {
    super(id, email, password, role);
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.isVerified = true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return phoneNumber != null && Objects.equals(phoneNumber, user.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(phoneNumber);
  }
}
