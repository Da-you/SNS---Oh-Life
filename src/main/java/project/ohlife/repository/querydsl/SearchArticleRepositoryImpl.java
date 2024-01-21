package project.ohlife.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import project.ohlife.domain.article.Article;

@RequiredArgsConstructor
public class SearchArticleRepositoryImpl{
  private final JPAQueryFactory queryFactory;


}
