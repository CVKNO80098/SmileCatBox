package org.learn.testspring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.learn.testspring.bean.CookieBean;
import org.learn.testspring.bean.UserBean;

//这个是DAO接口文件，用XML实现
public interface UserMapper {
    // 获取登录
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    UserBean getInfo(@Param("username") String username, @Param("password") String password);
    // 注册新用户
    @Insert("INSERT INTO user(nickname, username, password) VALUES (#{nickname}, #{username}, #{password})")
    void insertUser(UserBean userBean);
    // 通过ID寻找
    @Select("SELECT * FROM user WHERE id = #{id}")
    UserBean getFromId(int id);
}

