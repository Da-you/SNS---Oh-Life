package project.ohlife.service;

import static project.ohlife.domain.like.Like.doLike;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.user.User;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;
import project.ohlife.repository.ArticleRepository;
import project.ohlife.repository.LikeRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final ArticleRepository articleRepository;
  private final LikeRepository likeRepository;

  @Transactional
  public void like(User user, Long articleId) {
    Article article = articleRepository.findById(articleId).orElseThrow(() -> new CustomException(
        ErrorCode.ARTICLE_NOT_FOUND));
    if (checkLike(user, article)) {
      unlike(user, article);
    } else {
      likeRepository.save(doLike(article, user));
    }
  }


  public boolean checkLike(User user, Article article) {
    return likeRepository.existsByArticleAndUser(article, user);
  }

  public void unlike(User user, Article article) {
    likeRepository.deleteByArticleAndUser(article, user);
  }
}
