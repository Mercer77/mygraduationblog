package com.mercer.myblog.service;

import com.mercer.myblog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ Date:2019/9/23
 * Auther:Mercer
 * Auther:麻前程
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);//根据所属博客ID查询所有留言信息
    Comment saveComment(Comment comment);           //发表留言
    Page<Comment> list(Pageable pageable);
    void delete(Long id);
}
