package project.ohlife.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.article.ArticleComment;
import project.ohlife.domain.user.User;
import project.ohlife.exception.CustomException;
import project.ohlife.exception.ErrorCode;
import project.ohlife.repository.ArticleCommentRepository;
import project.ohlife.repository.ArticleRepository;
import project.ohlife.repository.dto.ArticleCommentDto.WriteArticleCommentRequest;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {

  private final ArticleCommentRepository articleCommentRepository;
  private final ArticleRepository articleRepository;

  @Transactional
  public void writeComment(User user, Long articleId, WriteArticleCommentRequest request) {
    // 게시글 확인
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
    // 댓글 작성
    articleCommentRepository.save(request.toEntity(article.getUser(), article));

  }

  // 댓글 수정
  @Transactional
  public void updateComment(User user, Long articleId, Long commentId,
      WriteArticleCommentRequest request) {
    // 게시글 확인
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
    // 댓글 작성
    ArticleComment articleComment = articleCommentRepository.findByIdAndArticle(commentId, article);
    if (articleComment == null) {
      throw new CustomException(ErrorCode.ARTICLE_COMMENT_NOT_FOUND);
    }
    if (!articleComment.getUser().equals(user)) {
      throw new CustomException(ErrorCode.UNAUTHENTICATED_USER);
    }
    articleComment.update(request.getComment());

  }

  //댓글 삭제
  @Transactional
  public void deleteComment(User user, Long articleId, Long commentId) {
    // 게시글 확인
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
    // 댓글 작성
    ArticleComment articleComment = articleCommentRepository.findByIdAndArticle(commentId, article);
    if (articleComment == null) {
      throw new CustomException(ErrorCode.ARTICLE_COMMENT_NOT_FOUND);
    }
    if (!articleComment.getUser().equals(user) || !article.getUser().equals(user)) {
      throw new CustomException(ErrorCode.UNAUTHENTICATED_USER);
    }
    articleCommentRepository.delete(articleComment);

  }

}
