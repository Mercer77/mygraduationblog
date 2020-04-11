package com.mercer.myblog.controller.admin;

import com.mercer.myblog.entity.User;
import com.mercer.myblog.service.CommentService;
import com.mercer.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @ Date:2019/8/18
 * Auther:Mercer
 * Auther:麻前程
 */
@Controller
@RequestMapping("/admin")
public class CommentManageController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public String loginPage(@PageableDefault(size = 8,sort = "createTime",direction = Sort.Direction.DESC)
                            Pageable pageable,
                            Model model){
        model.addAttribute("page",commentService.list(pageable));
        return "admin/comments";
    }

    @GetMapping("/comments/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        commentService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        attributes.addFlashAttribute("code","1");
        return "redirect:/admin/comments";

    }

}
