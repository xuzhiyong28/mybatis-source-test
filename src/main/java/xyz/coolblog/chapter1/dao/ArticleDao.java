package xyz.coolblog.chapter1.dao;

import org.apache.ibatis.annotations.Param;
import xyz.coolblog.chapter1.model.Article;

import java.util.List;

/**
 * ArticleDao
 *
 * @author Tian ZhongBo
 * @date 2018-07-01 13:20:51
 */
public interface ArticleDao {
    List<Article> findByAuthorAndCreateTime(@Param("author") String author, @Param("createTime") String createTime);
}
