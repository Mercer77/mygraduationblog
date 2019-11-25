package com.mercer.myblog.web.admin;

import com.mercer.myblog.po.Blog;
import com.mercer.myblog.po.Type;
import com.mercer.myblog.service.BlogService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @ Date:2019/8/21
 * Auther:Mercer
 * Auther:麻前程
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    private static String INPUT = "admin/blogs-input";      //博客新增页面
    private static String LIST = "admin/blogs";             //博客列表页面
    private static String RE_LIST = "redirect:/admin/blogs";//重定向博客列表

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
//    博客列表页面
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("page", blogService.list(pageable, blog));
        model.addAttribute("type", typeService.list());
        return LIST;
    }
//    博客多条件查询
    @PostMapping("/blogs/search")
    public String blogsSearch(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("page", blogService.list(pageable, blog));
        return "admin/blogs :: blogList";
    }
//    博客新增页面
    @GetMapping("/blogs/input")
    public String toInput(Model model){
        Blog blog = new Blog();
        blog.setId(null);
        blog.setType(new Type());
        model.addAttribute("blog", blog);//方便与更新页面公用，初始化一个空的blog对象
        setTagsAndTypes(model);
        return INPUT;
    }
    private void setTagsAndTypes(Model model){
        model.addAttribute("type", typeService.list());
        model.addAttribute("tag", tagService.list());
    }
//    博客更新页面
    @GetMapping("/blogs/{id}/input")
    public String toInput(@PathVariable("id") Long id, Model model){
        Blog blog = blogService.get(id);
        blog.initTagIds();
        model.addAttribute("blog", blog);//传一个要更新的blog信息
        setTagsAndTypes(model);
        return INPUT;
    }


    //    博客新增与更新
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setType(typeService.get(blog.getType().getId()));
        blog.setTags(tagService.list(blog.getTagsId()));
        Blog b = blogService.save(blog);
        if (b==null){
            attributes.addFlashAttribute("message","操作失败");
            attributes.addFlashAttribute("code","0");
        }
        else {
            attributes.addFlashAttribute("message","操作成功");
            attributes.addFlashAttribute("code","1");
        }

        return RE_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable("id") Long id ,RedirectAttributes attributes){
        blogService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        attributes.addFlashAttribute("code","1");
        return RE_LIST;
    }
}
