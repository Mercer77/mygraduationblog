package com.mercer.myblog.controller.admin;

import com.mercer.myblog.entity.User;
import com.mercer.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @ Date:2019/8/18
 * Auther:Mercer
 * Auther:麻前程
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @GetMapping("/index")
    public String indexPage(){
        return "admin/index";
    }
    @GetMapping("/userLogin")
    public String userLoginPage(){
        return "login";
    }
    @GetMapping("/userRegister")
    public String userRegisterPage(){
        return "register";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if (user!=null){
            user.setPassword(null);     //前端不要放密码，不安全
            session.setAttribute("user", user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message", "登陆失败，用户名或密码错误！");
            return "redirect:/admin";
        }
    }

    @PostMapping("/register")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String avatar,
                        @RequestParam String email,
                        RedirectAttributes attributes){

        User user = userService.checkUser(username,password);
        if (user!=null){
            //注册失败
            attributes.addFlashAttribute("message", "注册失败，已存在！");
            attributes.addFlashAttribute("code", "0");
        }else {
            if (avatar==null || avatar.equals(""))
                avatar="/images/head2.png";
            User user1 = new User();
            user1.setUsername(username);
            user1.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            user1.setAvatar(avatar);
            user1.setEmail(email);
            User user2 = userService.save(user1);
            if (user2!=null){
                attributes.addFlashAttribute("message", "注册成功！");
                attributes.addFlashAttribute("code", "1");
            }
        }
        return "register";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
