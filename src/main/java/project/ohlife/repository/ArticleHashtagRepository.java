package project.ohlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ohlife.domain.article.ArticleHashtag;

public interface ArticleHashtagRepository extends JpaRepository<ArticleHashtag, Long> {

}
