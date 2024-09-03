package org.learn.testspring.controller;

import org.learn.testspring.bean.NoticeBean;
import org.learn.testspring.bean.UserBean;
import org.learn.testspring.dto.postIdDto;
import org.learn.testspring.service.CookiesService;
import org.learn.testspring.service.NoticeService;
import org.learn.testspring.service.StarService;
import org.learn.testspring.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final CookiesService cookiesService;
    private final UserService userService;
    private final NoticeService noticeService;
    private final StarService starService;

    public UserController(CookiesService cookiesService, UserService userService, NoticeService noticeService, StarService starService) {
        this.cookiesService = cookiesService;
        this.userService = userService;
        this.noticeService = noticeService;
        this.starService = starService;
    }

    /***
     * 获取个人信息
     * @param cookie 需要带Cookie
     * @return 返回用户信息
     */
    @PostMapping("/userInfo")
    public UserBean getUser(@CookieValue(name = "cookie", defaultValue = "None") String cookie, @RequestBody int id) {
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return null;
        }

        UserBean result = userService.getFromId(id);
        if (result != null) {
            result.setPassword(null);
            return result;
        }
        return null;
    }

    @RequestMapping("/notice")
    public List<NoticeBean> getNotice(@CookieValue(name = "cookie", defaultValue = "None") String cookie) {
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return null;
        }
        return noticeService.getNoticeLs(userBean.getId());
    }
    @RequestMapping("/notice/read")
    public Boolean NoticeIsRead(@CookieValue(name = "cookie", defaultValue = "None") String cookie, @RequestBody int post_id) {
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return null;
        }
        return noticeService.readNotice(userBean.getId(), post_id);
    }

    @RequestMapping("/postStar")
    public String postStar(@CookieValue(name = "cookie", defaultValue = "None") String cookie, @RequestBody postIdDto post_id) {
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return null;
        }

        if(starService.createStar(userBean.getId(), post_id.getPost_id())) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/isStar")
    public Boolean isStar(@CookieValue(name = "cookie", defaultValue = "None") String cookie, @RequestBody postIdDto post_id) {
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return null;
        }

        return starService.isStar(userBean.getId(), post_id.getPost_id());
    }

    @RequestMapping("/CancelStar")
    public String cancelStar(@CookieValue(name = "cookie", defaultValue = "None") String cookie, @RequestBody postIdDto post_id) {
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return null;
        }

        if(starService.deleteStar(userBean.getId(), post_id.getPost_id())) {
            return "success";
        }
        return "fail";
    }
}
