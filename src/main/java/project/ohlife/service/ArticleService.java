package project.ohlife.service;

import static project.ohlife.exception.ErrorCode.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.ohlife.common.utils.s3.FileNameUtils;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.user.User;
import project.ohlife.exception.CustomException;
import project.ohlife.repository.ArticleRepository;
import project.ohlife.repository.dto.ArticleDto.WriteArticleRequest;
import project.ohlife.service.s3.AwsS3Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final AwsS3Service s3Service;


  @Transactional
  public void writeArticle(User user, WriteArticleRequest request, MultipartFile imageFile) {
    if (request.getImageUrl() != null) {
      String imageUrl = s3Service.uploadImage(imageFile);
      request.setImageUrl(imageUrl);
    }
    Article article = request.toEntity(user);
    log.info("article content = {}", article.getContent());
    log.info("article image = {}", article.getImageUrl());
    articleRepository.save(article);
  }

  @Transactional
  public void updateArticle(User user, Long articleId,
      WriteArticleRequest updateRequest, MultipartFile imageFile) {
    Article article = articleRepository.findById(articleId).orElseThrow(() -> new CustomException(
        ARTICLE_NOT_FOUND));
    if (!article.getUser().equals(user)) {
      throw new CustomException(UNAUTHENTICATED_USER);
    }
    String originImageUrl = article.getImageUrl();
    String updateImageUrl = updateRequest.getImageUrl();

    if (isDeleteSavedImage(originImageUrl, updateImageUrl, imageFile)) {
      String key = FileNameUtils.getFileName(originImageUrl);
      s3Service.deleteImage(key);
      updateRequest.deleteImageUrl();
    }
    if (updateRequest.getImageUrl() != null) {
      String imageUrl = s3Service.uploadImage(imageFile);
      updateRequest.setImageUrl(imageUrl);
    }
    article.update(updateRequest.getContent(), updateRequest.getImageUrl());

  }

  @Transactional
  public void deleteArticle(User user, Long articleId) {
    // 본인의 글인지 확인
    Article article = articleRepository.findById(articleId).orElseThrow(() -> new CustomException(
        ARTICLE_NOT_FOUND));
    if (!article.getUser().equals(user)) {
      throw new CustomException(UNAUTHENTICATED_USER);
    }
    // 삭제 로직 실행
    articleRepository.delete(article);

  }

  private boolean isDeleteSavedImage(String originImageUrl, String updatedImageUrl,
      MultipartFile imageFile) {
    return ((updatedImageUrl == null && originImageUrl != null) ||
        (originImageUrl != null && imageFile != null));
  }

}
