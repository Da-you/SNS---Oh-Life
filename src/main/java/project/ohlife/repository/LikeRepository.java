package project.ohlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.like.Like;
import project.ohlife.domain.user.User;

public interface LikeRepository extends JpaRepository<Like, Long> {

  boolean existsByArticleAndUser(Article article, User user);

  void deleteByArticleAndUser(Article article, User user);
}
