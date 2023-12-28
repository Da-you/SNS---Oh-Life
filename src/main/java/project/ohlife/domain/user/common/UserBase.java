package project.ohlife.domain.user.common;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import project.ohlife.common.BaseTimeEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn
public abstract class UserBase extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "user_id")
  private Long id;

  private String email;

  private String password;

  private UserRole role;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserBase userBase = (UserBase) o;
    return id != null && Objects.equals(id, userBase.id);
  }
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
