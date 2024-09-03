package org.learn.testspring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.learn.testspring.bean.NoticeBean;

import java.util.List;

public interface NoticeMapper {

    @Select("SELECT * FROM user_notice WHERE user_id=#{user_id}")
    List<NoticeBean> selectAll(int user_id);

    @Insert("INSERT INTO user_notice(user_id, message, type) VALUES (#{user_id}, #{message}, #{type})")
    int insert(NoticeBean noticeBean);

    @Update("UPDATE user_notice SET is_read='true' WHERE id=#{id} AND user_id=#{user_id}")
    int read(@Param("id") int id, @Param("user_id") int user_id);
}
