package org.learn.testspring.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.learn.testspring.bean.StarBean;

import java.util.List;

public interface StarMapper {
    @Insert("INSERT INTO post_stars(user_id, post_id) VALUES (#{user_id}, #{post_id})")
    int createStar(@Param("user_id") int user_id,@Param("post_id") int post_id);

    @Delete("DELETE FROM post_stars WHERE user_id=#{user_id} AND post_id=#{post_id}")
    int deleteStar(@Param("user_id") int user_id,@Param("post_id") int post_id);

    @Select("SELECT * FROM post_stars WHERE user_id=#{user_id}")
    List<StarBean> getStarByUser(int user_id);

    @Select("SELECT * FROM post_stars WHERE post_id=#{post_id}")
    List<StarBean> getStarByPost(int post_id);

    @Select("SELECT COUNT(*) FROM post_stars WHERE ${column}=#{post_id}")
    int getCountStar(@Param("column") String column, @Param("post_id") int post_id);

    @Select("SELECT COUNT(*) FROM post_stars WHERE post_id=#{post_id} AND user_id=#{user_id}")
    int isStar(int user_id, int post_id);
}
