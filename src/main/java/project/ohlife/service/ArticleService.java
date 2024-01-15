package project.ohlife.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ohlife.repository.ArticleHashtagRepository;
import project.ohlife.repository.ArticleRepository;
import project.ohlife.repository.HashtagRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final HashtagRepository hashtagRepository;
  private final ArticleHashtagRepository articleHashtagRepository;
}
