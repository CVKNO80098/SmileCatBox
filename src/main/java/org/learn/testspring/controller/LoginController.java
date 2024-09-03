package org.learn.testspring.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.learn.testspring.bean.UserBean;
import org.learn.testspring.service.CookiesService;
import org.learn.testspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

/**
 *  判断登录与Cookies生效的Class
 * @author 泠水 CVKNO80098@outlook.com
 * @version 0.0.1
 */
@Controller
public class LoginController {

    private final UserService userService;

    private final CookiesService cookiesService;

    public LoginController(UserService userService, CookiesService cookiesService) {
        this.userService = userService;
        this.cookiesService = cookiesService;
    }

    /**
     * 登录判断
     * @param cookie 自动传入，校验是否已经登录
     * @return 如果校验Cookies成功，直接跳转到主页，校验失败进入登录界面
     */
    @RequestMapping("/login")
    public String login(@CookieValue(name = "cookie", defaultValue = "None")String cookie) {
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return "login";
        }
        return "redirect:/detail";
    }

    /**
     * 校验登录是否成功
     * @param username 用户名
     * @param password 密码（后续版本会改为MD5）
     * @param model 传入值
     * @param response 用来打饼干钢印
     * @return 主页，登录失败则返回错误界面重新登陆
     */
    @PostMapping("/login/bc")
    public String loginIn(String username, String password, Model model, HttpServletResponse response) {

        UserBean ub = userService.loginIn(username, password);

        if (ub != null) {
            model.addAttribute("userid", ub.getId());
            model.addAttribute("nickname", ub.getNickname());
            model.addAttribute("password", ub.getPassword());
            System.out.println(ub.getNickname()+"(用户id为"+ub.getId()+")"+"登录成功！");

            String cookieText = cookiesService.setCookies(ub);
            Cookie cookie = new Cookie("cookie", cookieText);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/detail";
        } else {
            return "redirect:/WrongLogin";
        }
    }

    /**
     * 登录错误映射
     * @return 错误界面
     */
    @RequestMapping("/WrongLogin")
    public String WrongLogin() {
        return "WrongLogin";
    }
}
