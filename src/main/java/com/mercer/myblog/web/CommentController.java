package com.mercer.myblog.web;

import com.mercer.myblog.po.Comment;
import com.mercer.myblog.po.User;
import com.mercer.myblog.service.BlogService;
import com.mercer.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @ Date:2019/9/23
 * Auther:Mercer
 * Auther:麻前程
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Value("${comment.avator}")
    private String avator;


//    显示博客留言列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
//    回复留言
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        User user = (User) session.getAttribute("user");
        if (user!=null){
            comment.setAdminComment(true);
            comment.setAvatar(user.getAvatar());
        }
        else {
            comment.setAvatar(avator);
            comment.setAdminComment(false);
        }
        if (blogId!=null){
            commentService.saveComment(comment);
        }
        return "redirect:/comments/"+blogId;
    }

}
