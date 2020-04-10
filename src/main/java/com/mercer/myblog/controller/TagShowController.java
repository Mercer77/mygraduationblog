package com.mercer.myblog.controller;

import com.mercer.myblog.entity.Tag;
import com.mercer.myblog.service.BlogService;
import com.mercer.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @ Date:2019/11/22
 * Auther:Mercer
 * Auther:麻前程
 */
@Controller
public class TagShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;
    @GetMapping(value = "/tags/{id}")
    public String tags(@PageableDefault(size = 6,sort = "updateTime",direction = Sort.Direction.DESC)Pageable pageable,
                       @PathVariable Long id,
                       Model model){
        List<Tag> tags = tagService.listTop(10000);
        if (id ==-1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listByTagId(pageable, id));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
