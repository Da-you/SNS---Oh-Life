package project.ohlife.repository.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.user.User;

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



}
