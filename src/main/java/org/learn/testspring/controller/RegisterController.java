package org.learn.testspring.controller;

import org.learn.testspring.bean.UserBean;
import org.learn.testspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

/**
 * 用户注册的相关界面
 * @author 泠水 CVKNO80098@outlook.com
 * @version 0.0.1
 */
@Controller
public class RegisterController {
    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String register(@RequestParam(name="code", required = false) String code, Model model) {
        if (code != null) {
            if (code.equals("fail")) {
                model.addAttribute("error", "登录失败，可能是账户或者密码错误！");
                model.addAttribute("style","margin-left: auto;\n" +
                        "            margin-right: auto;\n" +
                        "            margin-top: 40px;\n" +
                        "            width: 220px;\n" +
                        "            height: 30px;\n" +
                        "            color: red;\n" +
                        "            border: 2px solid red;\n" +
                        "            border-radius: 5px;\n" +
                        "            background-color: #ffdede;\n" +
                        "            text-align: center;\n" +
                        "            opacity: 0;");
            }
        }
        return "register";
    }

    @RequestMapping("/register/bc")
    public String register(String nickname,String username, String password, Date birthday) {
        if (userService.register(nickname, username, password, birthday, 0)) {
            return "redirect:/register/success";
        } else {
            return "redirect:/register";
        }
    }

    @GetMapping("/register/success")
    public String success() {
        return "register-success";
    }
}
