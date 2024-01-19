package project.ohlife.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.ohlife.common.annotation.LoginUser;
import project.ohlife.domain.user.User;
import project.ohlife.repository.dto.ArticleCommentDto.WriteArticleCommentRequest;
import project.ohlife.repository.dto.ArticleDto.ArticleDetailResponse;
import project.ohlife.repository.dto.ArticleDto.ArticlesResponse;
import project.ohlife.repository.dto.ArticleDto.WriteArticleRequest;
import project.ohlife.response.CommonResponse;
import project.ohlife.response.PageResponse;
import project.ohlife.service.ArticleCommentService;
import project.ohlife.service.ArticleService;
import project.ohlife.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

  private final ArticleService articleService;
  private final UserService userService;
  private final ArticleCommentService articleCommentService;

  @PostMapping
  public CommonResponse<Void> writeArticle(@LoginUser String email,
      @RequestPart(value = "request") WriteArticleRequest request, @RequestPart(required = false)
  MultipartFile imageFile) {
    User user = userService.getUser(email);
    articleService.writeArticle(user, request, imageFile);
    return CommonResponse.ok();
  }

  @GetMapping
  public CommonResponse<PageResponse<ArticlesResponse>> getArticles(@LoginUser String email) {
    User user = userService.getUser(email);
    return CommonResponse.ok(articleService.getArticles(user));
  }
  @GetMapping("/{articleId}")
  public CommonResponse<ArticleDetailResponse> getArticleDetail(@LoginUser String email,
      @PathVariable Long articleId) {
    User user = userService.getUser(email);
    return CommonResponse.ok(articleService.getArticleDetail(user, articleId));
  }
  @PatchMapping("/{articleId}")
  public CommonResponse<Void> updateArticle(@LoginUser String email, @PathVariable Long articleId,
      @RequestBody WriteArticleRequest request,
      @RequestPart(required = false) MultipartFile imageFile) {
    User user = userService.getUser(email);
    articleService.updateArticle(user, articleId, request, imageFile);
    return CommonResponse.ok();
  }

  @DeleteMapping("/{articleId}")
  public CommonResponse<Void> deleteArticle(@LoginUser String email, @PathVariable Long articleId) {
    User user = userService.getUser(email);
    articleService.deleteArticle(user, articleId);
    return CommonResponse.ok();
  }

  @PostMapping("/{articleId}/comments")
  public CommonResponse<Void> writeComment(@LoginUser String email, @PathVariable Long articleId,
      @RequestBody WriteArticleCommentRequest request) {
    User user = userService.getUser(email);
    articleCommentService.writeComment(user, articleId, request);
    return CommonResponse.ok();
  }

  @PatchMapping("/{articleId}/comments/{commentId}")
  public CommonResponse<Void> updateComment(@LoginUser String email, @PathVariable Long articleId,
      @PathVariable Long commentId, @RequestBody WriteArticleCommentRequest request) {
    User user = userService.getUser(email);
    articleCommentService.updateComment(user, articleId, commentId, request);
    return CommonResponse.ok();
  }
  @DeleteMapping("/{articleId}/comments/{commentId}")
  public CommonResponse<Void> deleteComment(@LoginUser String email, @PathVariable Long articleId,
      @PathVariable Long commentId) {
    User user = userService.getUser(email);
    articleCommentService.deleteComment(user, articleId, commentId);
    return CommonResponse.ok();
  }

}
