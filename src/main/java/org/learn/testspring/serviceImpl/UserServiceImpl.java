package org.learn.testspring.serviceImpl;

import org.learn.testspring.bean.CookieBean;
import org.learn.testspring.bean.UserBean;
import org.learn.testspring.mapper.UserMapper;
import org.learn.testspring.service.CookiesService;
import org.learn.testspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final CookiesService cookiesService;

    @Autowired
    public UserServiceImpl(CookiesService cookiesService, UserMapper userMapper) {
        this.cookiesService = cookiesService;
        this.userMapper = userMapper;
    }

    @Override
    public UserBean getFromId(int id){
        return userMapper.getFromId(id);
    }

    @Override
    public UserBean loginIn(String username, String password) {
        return userMapper.getInfo(username, password);
    }

    @Override
    public Boolean register(String nickname, String username, String password, Date birthday, int power){
        if(userMapper.getInfo(username, password) != null){
            return false;
        } else {
            UserBean userBean = new UserBean();
            userBean.setNickname(nickname);
            userBean.setUsername(username);
            userBean.setPassword(password);
            userBean.setBirthday(birthday);
            userBean.setPower(power);
            userMapper.insertUser(userBean);
            return true;
        }
    }
}
