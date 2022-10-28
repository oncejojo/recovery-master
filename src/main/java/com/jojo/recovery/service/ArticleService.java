package com.jojo.recovery.service;

import com.jojo.recovery.model.Article;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 14:32
 * @Description
 * @Version 1.0
 */
public interface ArticleService {
    int insert(Article article);

    int update(Article article);

    Article getRecord(int id);

    List<Article> getList(Article article);

    int delete(int id);
}
