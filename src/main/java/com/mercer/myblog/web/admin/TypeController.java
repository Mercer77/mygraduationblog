package com.mercer.myblog.web.admin;

import com.mercer.myblog.po.Type;
import com.mercer.myblog.service.TypeService;
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
public class TypeController {
    @Autowired
    TypeService typeService;

    //查询所有分类，并分页
    @GetMapping("/types")
    public String types(@PageableDefault(size = 8,sort = "id",direction = Sort.Direction.ASC)
                        Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.list(pageable));
        return "admin/types";
    }
    //增加分类页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }
    //增加分类
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        if (typeService.getByName(type.getName())!=null){
            result.rejectValue("name", "ErrorName", "分类名称无法重复添加");  //名称重复校验
            return "admin/types-input";
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.save(type);
        if (t==null){
            attributes.addFlashAttribute("message", "新增失败");
            attributes.addFlashAttribute("code", "0");
        }
        else {
            attributes.addFlashAttribute("message", "新增成功");
            attributes.addFlashAttribute("code", "1");
        }
        return "redirect:/admin/types";
    }

    //编辑分类页面
    @GetMapping("/types/{id}/input")
    public String toEdit(@PathVariable Long id,Model model){
        model.addAttribute("type", typeService.get(id));
        return "admin/types-input";
    }
    //更新分类
    @PostMapping("/types/{id}")
    public String editInput(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        if (typeService.getByName(type.getName())!=null){
            result.rejectValue("name", "ErrorName", "分类名称无法重复添加");  //名称重复校验
            return "admin/types-input";
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.update(id, type);
        if (t==null){
            attributes.addFlashAttribute("message", "更新失败");
            attributes.addFlashAttribute("code", "0");
        }
        else {
            attributes.addFlashAttribute("message", "更新成功");
            attributes.addFlashAttribute("code", "1");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        attributes.addFlashAttribute("code","1");
        return "redirect:/admin/types";

    }

}
