package project.ohlife.repository.querydsl;

import static project.ohlife.domain.article.QArticle.article;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import project.ohlife.domain.article.Article;

@RequiredArgsConstructor
public class SearchArticleRepositoryImpl implements SearchArticleRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Article> getArticleListFindByKeyword(String keyword, Pageable pageable) {
    List<Article> articles = queryFactory.selectFrom(article)
        .where(article.content.contains(keyword))
        .offset(pageable.getOffset()) // offset이란 현재 페이지의 시작 번호를 의미한다.
        .limit(pageable.getPageSize()) // limit은 한 페이지에 보여줄 게시글의 수를 의미한다.
        .fetch();

    long totalCount = queryFactory.selectFrom(article)
        .where(article.content.contains(keyword))
        .fetchCount();

    return PageableExecutionUtils.getPage(articles, pageable, () -> totalCount);
  }

}
