package project.ohlife.domain.article;

import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.ohlife.common.BaseTimeEntity;
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ArticleHashtag extends BaseTimeEntity {

  private Long id;

  @ManyToOne
  @JoinColumn(name = "article_id")
  private Article article;

  @ManyToOne
  @JoinColumn(name = "hashtag_id")
  private Hashtag hashtag;
}
