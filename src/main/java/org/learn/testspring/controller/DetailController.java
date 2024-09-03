package org.learn.testspring.controller;

import org.learn.testspring.bean.UserBean;
import org.learn.testspring.service.CookiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 老版本的detail
 * @author 泠水 CVKNO80098@outlook.com
 */
@Controller
@RequestMapping("/detail")
public class DetailController {

    private final CookiesService cookiesService;

    @Autowired
    public DetailController(CookiesService cookiesService) {
        this.cookiesService = cookiesService;
    }

    @GetMapping()
    public String detail(@CookieValue(name = "cookie", defaultValue = "None")String cookie, Model model) {
        UserBean userBean = cookiesService.getCookies(cookie);
        model.addAttribute("userBean", userBean);
        if (userBean == null) {
            return "redirect:/login";
        }
        return "index";
    }
}
