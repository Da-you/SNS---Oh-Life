package project.ohlife.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ohlife.domain.user.User;
import project.ohlife.repository.ArticleCommentRepository;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {

  private final ArticleCommentRepository articleCommentRepository;

  public void writeComment(User user, Long articleId, String content) {
    // 게시글 확인

    // 댓글 작성

  }
  // 댓글 수정

  //댓글 삭제

}
