package org.learn.testspring.service;

import org.learn.testspring.bean.PostBean;
import org.learn.testspring.bean.UserBean;

import java.util.List;

public interface StarService {
    /**
     * @param user_id 用户ID
     * @param post_id 帖子ID
     * @return {@link Boolean} 是否成功
     */
    public Boolean createStar(int user_id, int post_id);
    /**
     * @param user_id 用户ID
     * @param post_id 帖子ID
     * @return {@link Boolean} 是否成功
     */
    public Boolean deleteStar(int user_id, int post_id);

    /**
     * @param user_id 用户ID
     * @return {@link List<PostBean>} 用户的关注列表
     */
    public List<PostBean> getStarPostByUser(int user_id);
    /**
     * @param post_id
     * @return {@link List<UserBean>} 关注此帖子的列表
     */
    public List<UserBean> getStarUserByPost(int post_id);

    /**
     * @param user_id 用户id
     * @param post_id 帖子id
     * @return {@link Boolean} 是否已经关注
     */
    public Boolean isStar(int user_id, int post_id);
}
