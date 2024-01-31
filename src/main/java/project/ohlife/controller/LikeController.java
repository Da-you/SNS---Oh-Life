package project.ohlife.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ohlife.common.annotation.LoginUser;
import project.ohlife.domain.user.User;
import project.ohlife.response.CommonResponse;
import project.ohlife.service.LikeService;
import project.ohlife.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

  private final LikeService likeService;
  private final UserService userService;


  @PostMapping("/{articleId}")
  public CommonResponse<Void> like(@LoginUser String email, @PathVariable Long articleId) {
    User user = userService.getUser(email);
    likeService.like(user, articleId);
    return CommonResponse.ok();
  }

}
