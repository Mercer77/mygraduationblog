package com.mercer.myblog.controller;

import com.mercer.myblog.entity.Type;
import com.mercer.myblog.service.BlogService;
import com.mercer.myblog.service.TypeService;
import com.mercer.myblog.vo.BlogQuery;
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
 * @ Date:2019/11/19
 * Auther:Mercer
 * Auther:麻前程
 */
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 6,sort = "updateTime",direction = Sort.Direction.DESC)
                                    Pageable pageable,
                                    Model model,
                                    @PathVariable Long id){
        List<Type> types = typeService.listTop(1000);
        if (id ==-1){
            id=types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);

        model.addAttribute("types",types);
        model.addAttribute("page", blogService.list(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
