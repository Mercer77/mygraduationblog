package com.mercer.myblog.web;

/**
 * @ Date:2019/8/17
 * Auther:Mercer
 * Auther:麻前程
 */

import com.mercer.myblog.service.BlogService;
import com.mercer.myblog.service.CommentService;
import com.mercer.myblog.service.TagService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @Autowired
    CommentService commentService;

    /**
     * 首页
     * @param pageable
     * @param model
     * @return
     */

    @GetMapping("/")
    public String index(@PageableDefault(size = 6,sort = "updateTime",direction = Sort.Direction.DESC)
                                    Pageable pageable,
                                    Model model){
        model.addAttribute("page", blogService.list(pageable));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        model.addAttribute("tags", tagService.listTop(10));
        model.addAttribute("types", typeService.listTop(6));
        return "index";
    }
//    搜索页面
    @PostMapping("/search")
    public String search(@PageableDefault(size = 4,sort = "updateTime",direction = Sort.Direction.DESC)
                                     Pageable pageable,
                                     Model model,
                                     @RequestParam String query){
        model.addAttribute("query", query);
        BlogQuery query1 = new BlogQuery();
        query1.setTitle("%"+query+"%");
        model.addAttribute("page", blogService.listSearch(pageable, query1));
        return "search";

    }
//    博客详情页面
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id")Long id,Model model){
        model.addAttribute("blog", blogService.getDetail(id));
        model.addAttribute("comments", commentService.listCommentByBlogId(id));
        return "blog";
    }




    @GetMapping("/about")
    public String about(Model model){

        return "about";
    }


}
