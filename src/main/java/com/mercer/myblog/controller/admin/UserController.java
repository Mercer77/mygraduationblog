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
import java.util.Date;

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

    /**
     * 管理员登录
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if (user!=null && user.getType()==1){
            user.setPassword(null);     //前端不要放密码，不安全
            session.setAttribute("user", user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message", "登陆失败，用户名或密码错误！");
            return "redirect:/admin";
        }
    }

    /**
     * 游客登录
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/frontLogin")
    public String frontLogin(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if (user!=null && user.getType()==0){
            user.setPassword(null);     //前端不要放密码，不安全
            session.setAttribute("ptUser", user);
            return "redirect:/";
        }else {
            attributes.addFlashAttribute("message", "登陆失败，用户名或密码错误！");
            return "redirect:/admin/userLogin";
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
            user1.setNickname(username);
            user1.setType(0);
            user1.setCreateTime(new Date());
            user1.setUpdateTime(new Date());
            User user2 = userService.save(user1);
            if (user2!=null){
                attributes.addFlashAttribute("message", "注册成功！");
                attributes.addFlashAttribute("code", "1");
            }
        }
        return "redirect:/admin/userRegister";

    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/exitPtUser")
    public String logoutUser(HttpSession session){
        session.removeAttribute("ptUser");
        return "redirect:/";
    }

}
