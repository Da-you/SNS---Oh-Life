package project.ohlife.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.user.User;

public interface SearchRepository {

  Page<Article> getArticleListFindByKeyword(String keyword, Pageable pageable);

  Page<User> getUserListFindByKeyword(String keyword, Pageable pageable);

}
