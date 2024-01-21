package project.ohlife.repository.querydsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.ohlife.domain.article.Article;

public interface SearchArticleRepository {

  Page<Article> getArticleListFindByKeyword(String keyword, Pageable pageable);

}
