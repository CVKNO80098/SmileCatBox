package org.learn.testspring.service;

import org.learn.testspring.bean.UserBean;

public interface CookiesService {
    public UserBean getCookies(String cookies);
    public String setCookies(UserBean userBean);
    public Boolean removeCookies(String cookies);
}
