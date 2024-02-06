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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import project.ohlife.domain.user.common.UserBase;
import project.ohlife.domain.user.common.UserRole;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "users")
@DiscriminatorValue("users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@NoArgsConstructor(access = PROTECTED)
public class User extends UserBase {

  private String nickname;

  private String profileImage;

  private String description;
  @Column(unique = true)
  private String phoneNumber;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;


  public static User createUser(String email, String password, UserRole role, String nickname,
      String phoneNumber) {
    User user = new User();
    user.email = email;
    user.password = password;
    user.role = role;
    user.nickname = nickname;
    user.phoneNumber = phoneNumber;
    return user;
  }

  @Builder
  public User(Long id, String email, String password, UserRole role, String nickname, String phoneNumber) {
    super(id, email, password, role);
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
  }

  public void withdrawal(String email, String phoneNumber) {
    this.deletedAt = LocalDateTime.now();
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public void updateProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }
  public void updateProfile(String nickname, String description) {
    this.nickname = nickname;
    this.description = description;
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
