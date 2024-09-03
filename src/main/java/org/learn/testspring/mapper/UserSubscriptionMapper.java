package org.learn.testspring.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.learn.testspring.bean.SubBean;

import java.util.List;

public interface UserSubscriptionMapper {
    // 获取关注人列表
    @Select("SELECT * FROM user_subscription WHERE follower=#{follower}")
    List<SubBean> getUserSubscription(int follower);

    @Select("SELECT * FROM user_subscription WHERE followee=#{followee}")
    List<SubBean> getUserSubscriptionByFollowee(int followee);

    // 新加关注
    @Insert("INSERT INTO user_subscription(follower, followee) VALUES (#{follower},#{followee})")
    int insertUserSubscription(@Param("follower") int follower, @Param("followee") int followee);

    @Delete("DELETE FROM user_subscription WHERE follower=#{follower} AND followee=#{followee}")
    int deleteUserSubscription(@Param("follower") int follower, @Param("followee") int followee);

    @Select("SELECT * FROM user_subscription WHERE id=#{id}")
    SubBean getSubById(int id);
}
