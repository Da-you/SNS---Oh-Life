package project.ohlife.domain.article;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.ohlife.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Hashtag extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @JoinColumn(name = "article_id", nullable = false)
  @ManyToOne(optional = false)
  private Article article;

  private String hashtagContent;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Hashtag)){
      return false;
    }
    Hashtag hashtag = (Hashtag) o;
    return Objects.equals(id, hashtag.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
