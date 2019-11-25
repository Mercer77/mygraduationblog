package com.mercer.myblog.web.admin;

import com.mercer.myblog.po.Tag;
import com.mercer.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @ Date:2019/8/21
 * Auther:Mercer
 * Auther:麻前程
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    TagService tagService;

    //查询所有标签，并分页
    @GetMapping("/tags")
    public String types(@PageableDefault(size = 8,sort = "id",direction = Sort.Direction.ASC)
                        Pageable pageable,
                        Model model){
        model.addAttribute("page",tagService.list(pageable));
        return "admin/tags";
    }
    //增加标签页面
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }
    //增加标签
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        if (tagService.getByName(tag.getName())!=null){
            result.rejectValue("name", "ErrorName", "标签名称无法重复添加");  //名称重复校验
            return "admin/tags-input";
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.save(tag);
        if (t==null){
            attributes.addFlashAttribute("message", "新增失败");
            attributes.addFlashAttribute("code", "0");
        }
        else {
            attributes.addFlashAttribute("message", "新增成功");
            attributes.addFlashAttribute("code", "1");
        }
        return "redirect:/admin/tags";
    }

    //编辑标签页面
    @GetMapping("/tags/{id}/input")
    public String toEdit(@PathVariable Long id,Model model){
        model.addAttribute("tag", tagService.get(id));
        return "admin/tags-input";
    }
    //更新标签
    @PostMapping("/tags/{id}")
    public String editInput(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        if (tagService.getByName(tag.getName())!=null){
            result.rejectValue("name", "ErrorName", "标签名称无法重复添加");  //名称重复校验
            return "admin/tags-input";
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.update(id, tag);
        if (t==null){
            attributes.addFlashAttribute("message", "更新失败");
            attributes.addFlashAttribute("code", "0");
        }
        else {
            attributes.addFlashAttribute("message", "更新成功");
            attributes.addFlashAttribute("code", "1");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        attributes.addFlashAttribute("code","1");
        return "redirect:/admin/tags";

    }

}
