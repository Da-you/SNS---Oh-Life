package project.ohlife.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ohlife.service.ArticleCommentService;
import project.ohlife.service.ArticleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

  private final ArticleService articleService;
  private final ArticleCommentService articleCommentService;

}
