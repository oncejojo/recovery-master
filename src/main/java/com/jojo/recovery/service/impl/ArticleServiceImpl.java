package com.jojo.recovery.service.impl;

import com.jojo.recovery.mapper.ArticleMapper;
import com.jojo.recovery.model.Article;
import com.jojo.recovery.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 14:33
 * @Description
 * @Version 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public int insert(Article article) {
        return articleMapper.insertSelective(article);
    }

    @Override
    public int update(Article article) {
        return articleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public Article getRecord(int id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Article> getList(Article article) {
        return articleMapper.getList(article);
    }

    @Override
    public int delete(int id) {
        return articleMapper.delete(id);
    }
}
