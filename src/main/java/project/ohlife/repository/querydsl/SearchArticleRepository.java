package project.ohlife.repository.querydsl;

import java.util.List;
import project.ohlife.domain.article.Article;

public interface SearchArticleRepository {

  List<Article> getArticleListFindByKeyword(String keyword);

}
