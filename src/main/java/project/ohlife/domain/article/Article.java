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
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import project.ohlife.common.BaseTimeEntity;
import project.ohlife.domain.user.User;

@Entity
@Getter
@SQLDelete(sql = "UPDATE articles SET deleted_at = NOW() WHERE id = ?")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Article extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(length = 1000)
  private String content;

  private LocalDateTime deletedAt;

  private String imageUrl;
  @OrderBy("createdAt DESC")
  @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true)
  private Set<ArticleComment> articleComments = new LinkedHashSet<>();
  @Builder
  public Article(User user, String content, String imageUrl) {
    this.user = user;
    this.content = content;
    this.imageUrl = imageUrl;
  }

  public void update(String content, String imageUrl) {
    this.content = content;
    this.imageUrl = imageUrl;
  }


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
