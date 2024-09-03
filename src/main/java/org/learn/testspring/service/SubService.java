package org.learn.testspring.service;

import org.learn.testspring.bean.UserBean;

import java.util.List;

public interface SubService {
    // 标准调用
    public Boolean createSubscribe(int follower_id, int followee_id);
    public Boolean deleteSubscribe(int follower_id, int followee_id);

    // 相互获取
    public List<UserBean> getFollowee (int follower_id);
    public List<UserBean> getFollower (int followee_id);

    /**
     * 通过ID查询一组关注关系
     * @param id 传入Sub的唯一ID
     * @return {@link List}列表第一项为Follower，第二项为Followee
     */
    public List<UserBean> getSubFromId (int id);
}
