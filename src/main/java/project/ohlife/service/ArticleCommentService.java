package project.ohlife.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ohlife.repository.ArticleCommentRepository;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {

  private final ArticleCommentRepository articleCommentRepository;

}
