package project.ohlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ohlife.domain.article.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
