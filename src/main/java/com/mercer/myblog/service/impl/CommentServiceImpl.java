package com.mercer.myblog.service.impl;

import com.mercer.myblog.dao.BlogRepository;
import com.mercer.myblog.dao.CommentRepository;
import com.mercer.myblog.po.Comment;
import com.mercer.myblog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Date:2019/9/23
 * Auther:Mercer
 * Auther:麻前程
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogRepository blogRepository;


    /**
     * 获取博客留言列表
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    /**
     * 保存留言
     * @param comment
     * @return
     */
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        Long blogId = comment.getBlog().getId();
        if (parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        comment.setBlog(blogRepository.findBlogById(blogId));
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶级的留言节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并留言的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * 存放迭代找出的所有子代的集合
     */
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 将所有子代的对象放到第一级子代集合中
     * @param comments  root根节点，blog不为空的对象集合
     */
    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                //循环迭代，找出子代，都放在tempreplys中
                recursively(reply);
            }
            //修改顶级节点的replyComments为迭代后的集合
            comment.setReplyComments(tempReplys);
            //清除临时数据
            tempReplys=new ArrayList<>();
        }

    }

    /**
     * 递归迭代所有有子节点的留言，剥洋葱
     * @param comment
     */
    private void recursively(Comment comment){
        tempReplys.add(comment);
        if (comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }


    }

}
