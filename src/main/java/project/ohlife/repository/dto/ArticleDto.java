package project.ohlife.repository.dto;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.article.ArticleComment;
import project.ohlife.domain.user.User;
import project.ohlife.repository.dto.ArticleCommentDto.ArticleCommentResponse;

public class ArticleDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class WriteArticleRequest {

    @Nullable
    private String imageUrl;
    private String content;


    public Article toEntity(User user) {
      return Article.builder()
          .user(user)
          .content(content)
          .imageUrl(imageUrl)
          .build();
    }

    public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
    }

    public void deleteImageUrl() {
      this.imageUrl = null;
    }
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ArticlesResponse {

    private String profileImageUrl;
    private String nickname;
    private List<String> imageUrls;
    private String content;

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ArticleDetailResponse {

    private ArticlesResponse articlesResponse;
    private List<ArticleCommentResponse> comments; // 댓글 작성자의 이름과 프로필도 가져와야하기에 별도의 DTO 생성 필요
  }


}
