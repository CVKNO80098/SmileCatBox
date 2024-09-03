package org.learn.testspring.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.learn.testspring.bean.CookieBean;
import org.learn.testspring.bean.UserBean;

public interface CookieMapper {
    // 获取用户所拥有的Cookies
    @Select("SELECT * FROM cookies WHERE COOKIE = #{cookieText}")
    CookieBean getFromCookie(String cookieText);
    // 生成对应用户的Cookies
    @Insert("INSERT INTO cookies(cookie, userid) VALUES (#{cookieText}, #{user.id})")
    void setCookie(@Param("user") UserBean user, @Param("cookieText") String cookieText);
    //删除用户对应Cookie
    @Delete("DELETE FROM cookies WHERE COOKIE = #{cookieText}")
    int removeCookie(String cookieText);
}
