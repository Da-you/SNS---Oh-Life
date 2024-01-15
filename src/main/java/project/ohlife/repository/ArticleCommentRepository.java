package project.ohlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ohlife.domain.article.Article;
import project.ohlife.domain.article.ArticleComment;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

}
