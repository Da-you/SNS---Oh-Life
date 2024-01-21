package project.ohlife.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.ohlife.domain.article.Article;
import project.ohlife.repository.querydsl.SearchArticleRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, SearchArticleRepository {

  Page<Article> findAll(Pageable pageable);

}
