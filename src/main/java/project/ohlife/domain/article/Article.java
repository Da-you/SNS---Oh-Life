package project.ohlife.domain.article;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.ohlife.common.BaseTimeEntity;
import project.ohlife.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Article extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(length = 1000, nullable = false)
  private String content;

  private String imageUrl;
  @OrderBy("createdAt DESC")
  @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true)
  private Set<ArticleComment> articleComments = new LinkedHashSet<>();


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Article)) {
      return false;
    }
    Article article = (Article) o;
    return id != null && Objects.equals(id, article.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
