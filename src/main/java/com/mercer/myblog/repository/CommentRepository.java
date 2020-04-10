package com.mercer.myblog.repository;

import com.mercer.myblog.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ Date:2019/9/23
 * Auther:Mercer
 * Auther:麻前程
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
