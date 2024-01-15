package project.ohlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.article.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

}
