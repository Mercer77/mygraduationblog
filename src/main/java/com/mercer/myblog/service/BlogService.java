package com.mercer.myblog.service;

import com.mercer.myblog.po.Blog;
import com.mercer.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @ Date:2019/8/23
 * Auther:Mercer
 * Auther:麻前程
 */
public interface BlogService {
    Blog get(Long id);
    Blog getDetail(Long id);
    Page<Blog> list(Pageable pageable, BlogQuery blog);
    Page<Blog> list(Pageable pageable);
    Page<Blog> listByTagId(Pageable pageable,Long tagId);
    Page<Blog> listSearch(Pageable pageable,String query);
    Page<Blog> listRecommendBlogTop(Integer size);
    Blog save(Blog blog);
    Blog update(Long id,Blog blog);
    void delete(Long id);
}
