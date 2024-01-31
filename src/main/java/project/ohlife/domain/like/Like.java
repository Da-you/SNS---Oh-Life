package project.ohlife.domain.like;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.user.User;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
public class Like {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "article_id")
  private Article article;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;


  public static Like doLike(Article article, User user) {
    Like like = new Like();
    like.article = article;
    like.user = user;
    return like;
  }


}
