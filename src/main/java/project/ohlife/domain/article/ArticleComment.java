package project.ohlife.domain.article;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.ohlife.common.BaseTimeEntity;
import project.ohlife.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ArticleComment extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @JoinColumn(name = "user_id", nullable = false)
  @ManyToOne(fetch = LAZY, optional = false)
  private User user;

  @JoinColumn(name = "article_id", nullable = false)
  @ManyToOne(fetch = LAZY, optional = false)
  private Article article;

  @Column(length = 1000)
  private String content;

  @Builder
  public ArticleComment(User user, Article article, String content) {
    this.user = user;
    this.article = article;
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ArticleComment)) {
      return false;
    }
    ArticleComment that = (ArticleComment) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public void update(String comment) {
    this.content = comment;
  }
}
