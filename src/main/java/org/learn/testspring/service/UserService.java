package org.learn.testspring.service;

import org.learn.testspring.bean.CookieBean;
import org.learn.testspring.bean.NoticeBean;
import org.learn.testspring.bean.UserBean;

import java.sql.Date;
import java.util.List;

//Service层

/***
 * 用于用户服务层面的通用函数
 * @version 0.0.1
 * @author 泠水 CVKNO80098@outlook.com
 */
public interface UserService {

    /**
     * 获取账户信息
     * @param id 用户ID
     * @return {@link UserBean} 用户信息
     */
    UserBean getFromId(int id);
    /**
     * 登录函数
     * @param username 用户名
     * @param password 用户密码
     * @return {@link UserBean}从数据库拉取一下返回
     */
    UserBean loginIn(String username, String password);

    /**
     * 注册函数
     * @param nickname 昵称
     * @param username 用户名
     * @param password 密码
     * @param birthday 生日
     * @param power 权限
     * @return {@link Boolean}是否注册成功
     */
    Boolean register(String nickname, String username, String password, Date birthday, int power);
}
