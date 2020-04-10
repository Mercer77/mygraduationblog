package com.mercer.myblog.controller;

import com.mercer.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ Date:2019/11/26
 * Auther:Mercer
 * Auther:麻前程
 */
@Controller
public class ArchivesShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archivesBlog",blogService.getArchivesBlog());
        model.addAttribute("countBlog",blogService.getBlogCount());
        return "archives";
    }
}
