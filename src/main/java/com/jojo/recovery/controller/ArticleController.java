package com.jojo.recovery.controller;

import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.model.Article;
import com.jojo.recovery.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JoJo
 * @Date 2022/6/1 16:45
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping("/insert")
    public AjaxResult insertRecord(Article article) {
        articleService.insert(article);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult updateRecord(Article article) {
        Article record = articleService.getRecord(article.getId());
        if (record == null) {
            return AjaxResult.error(JsonResultEnum.TARGET_NULL);
        }
        articleService.update(article);
        return AjaxResult.success();
    }

    @PostMapping("/delete")
    public AjaxResult delete(Integer id) {
        articleService.delete(id);
        return AjaxResult.success();
    }
}
