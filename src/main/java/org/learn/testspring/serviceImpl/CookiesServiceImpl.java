package org.learn.testspring.serviceImpl;

import org.learn.testspring.bean.CookieBean;
import org.learn.testspring.bean.UserBean;
import org.learn.testspring.mapper.CookieMapper;
import org.learn.testspring.mapper.UserMapper;
import org.learn.testspring.service.CookiesService;
import org.learn.testspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CookiesServiceImpl implements CookiesService {

    private final UserMapper userMapper;

    private final CookieMapper cookieMapper;

    public CookiesServiceImpl(UserMapper userMapper, CookieMapper cookieMapper) {
        this.userMapper = userMapper;
        this.cookieMapper = cookieMapper;
    }

    @Override
    public UserBean getCookies(String cookies) {
        try {
            CookieBean cookieBean = cookieMapper.getFromCookie(cookies);
            return userMapper.getFromId(cookieBean.getUserId());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String setCookies(UserBean userBean) {
        String cookie = UUID.randomUUID().toString();
        cookieMapper.setCookie(userBean, cookie);
        return cookie;
    }

    @Override
    public Boolean removeCookies(String cookies) {
        try {
            if (cookieMapper.removeCookie(cookies) < 1) {
                System.out.println("尝试删除一个Cookie"+cookies+"时失败，可能是Cookie不存在");
            }
            return true;
        } catch (Exception e) {
            System.err.println("删除Cookie时产生报错"+e.getMessage());
            return false;
        }
    }
}
