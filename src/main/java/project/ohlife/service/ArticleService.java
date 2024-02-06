package project.ohlife.service;

import static project.ohlife.exception.ErrorCode.*;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.ohlife.common.utils.s3.FileNameUtils;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.user.User;
import project.ohlife.exception.CustomException;
import project.ohlife.repository.ArticleRepository;
import project.ohlife.repository.LikeRepository;
import project.ohlife.repository.dto.ArticleCommentDto.ArticleCommentResponse;
import project.ohlife.repository.dto.ArticleDto.ArticleDetailResponse;
import project.ohlife.repository.dto.ArticleDto.ArticlesResponse;
import project.ohlife.repository.dto.ArticleDto.WriteArticleRequest;
import project.ohlife.response.PageResponse;
import project.ohlife.service.s3.AwsS3Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final LikeRepository likeRepository;
  private final AwsS3Service s3Service;

  // 게시글 전체 조회 조건이 없는
  @Transactional(readOnly = true)
  public PageResponse<ArticlesResponse> getArticles(User user) {
    Page<Article> articles = articleRepository.findAll(PageRequest.of(0, 10));



    List<ArticlesResponse> contents = articles.stream()
        .map(article -> new ArticlesResponse(article.getId(),article.getUser().getProfileImage(),
            article.getUser().getNickname(), article.getImageUrl(),
            article.getContent())).toList();

    return PageResponse.of(articles, contents);
  }

  public PageResponse<ArticlesResponse> getArticlesByKeyword(User user, String keyword) {
    Page<Article> articles = articleRepository.getArticleListFindByKeyword(keyword,
        PageRequest.of(0, 10));


    List<ArticlesResponse> contents = articles.stream()
        .map(article -> new ArticlesResponse(article.getId(),article.getUser().getProfileImage(),
            article.getUser().getNickname(), article.getImageUrl(),
            article.getContent())).toList();

    return PageResponse.of(articles, contents);
  }

  @Transactional(readOnly = true)
//  @Cacheable(value = "article", key = "#articleId")
  public ArticleDetailResponse getArticleDetail(User user, Long articleId) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new CustomException(ARTICLE_NOT_FOUND));
    User findUser = article.getUser();

    ArticlesResponse articles = new ArticlesResponse(article.getId(),findUser.getProfileImage(),
        findUser.getNickname(), article.getImageUrl(), article.getContent());

    boolean isLike = likeRepository.existsByArticleAndUser(article, user);
    Integer likeCount = likeRepository.countByArticle(article);

    List<ArticleCommentResponse> comments = article.getArticleComments().stream()
        .map(articleComment -> new ArticleCommentResponse(articleComment.getUser().getNickname(),
            articleComment.getUser().getProfileImage(),
            articleComment.getContent()))
        .toList();

    return new ArticleDetailResponse(articles, comments, isLike, likeCount);


  }


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
//  @CacheEvict(value = "article", key = "#articleId")
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
//  @CacheEvict(value = "article", key = "#articleId")
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
