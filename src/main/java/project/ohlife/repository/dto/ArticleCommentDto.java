package project.ohlife.repository.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.article.ArticleComment;
import project.ohlife.domain.user.User;

public class ArticleCommentDto {
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class WriteArticleCommentRequest {
    private String comment;


    public ArticleComment toEntity(User user,Article article) {
      return ArticleComment.builder()
          .user(user)
          .article(article)
          .content(comment)
          .build();
    }
  }
  //articleCommentResponse
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ArticleCommentResponse implements Serializable {
    private String nickname;
    private String profileImageUrl;
    private String content;
  }

}
