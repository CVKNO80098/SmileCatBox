package org.learn.testspring.controller;

import org.learn.testspring.service.CookiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexMoreController {

    private final CookiesService cookiesService;

    @Autowired
    public IndexMoreController(CookiesService cookiesService) {
        this.cookiesService = cookiesService;
    }

    /***
     * 在数据库中删除本机所带Cookie
     * @param cookie 自动识别
     * @return 是否成功
     */
    @RequestMapping("/delete-cookies")
    public String deleteCookies(@CookieValue(name = "cookie", defaultValue = "None") String cookie) {
        try {
            cookiesService.removeCookies(cookie);
            return "redirect:/login";
        } catch (Exception e) {
            return "redirect:/index";
        }
    }
}
