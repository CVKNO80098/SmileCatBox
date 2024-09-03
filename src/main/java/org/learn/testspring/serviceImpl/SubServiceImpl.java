package org.learn.testspring.serviceImpl;

import org.learn.testspring.bean.SubBean;
import org.learn.testspring.bean.UserBean;
import org.learn.testspring.mapper.UserMapper;
import org.learn.testspring.mapper.UserSubscriptionMapper;
import org.learn.testspring.service.SubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubServiceImpl implements SubService {

    private final UserSubscriptionMapper userSubscriptionMapper;

    private final UserMapper userMapper;

    public SubServiceImpl(UserSubscriptionMapper userSubscriptionMapper, UserMapper userMapper) {
        this.userSubscriptionMapper = userSubscriptionMapper;
        this.userMapper = userMapper;
    }


    /**
     * @param follower_id 订阅者
     * @param followee_id 被订阅者
     * @return {@link Boolean} 是否成功
     */
    @Override
    public Boolean createSubscribe(int follower_id, int followee_id) {
        try {
            if (userSubscriptionMapper.insertUserSubscription(follower_id, followee_id) < 1) {
                System.err.println("用户关注可能未成功，请检查数据库更新情况");
                return false;
            } else {
                System.out.println(follower_id+"关注了"+followee_id);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @param follower_id 订阅者
     * @param followee_id 被订阅者
     * @return {@link Boolean} 是否成功
     */
    @Override
    public Boolean deleteSubscribe(int follower_id, int followee_id) {
        try {
            if (userSubscriptionMapper.deleteUserSubscription(follower_id, followee_id) < 1) {
                System.err.println("用户取消关注可能未成功，请检查数据库更新情况");
                return false;
            } else {
                System.out.println(follower_id+"取消关注了"+followee_id);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 通过关注者ID获取被关注者列表
     * @param follower_id 关注者id
     * @return {@link List} 其关注的用户列表
     */
    @Override
    public List<UserBean> getFollowee(int follower_id) {
        List<SubBean> SQLGet =  userSubscriptionMapper.getUserSubscription(follower_id);
        List<UserBean> result = new ArrayList<UserBean>();

        for (SubBean subBean : SQLGet) {
            UserBean userBean = userMapper.getFromId(subBean.getFollowee());
            result.add(userBean);
        }

        return result;
    }

    /**
     * 通过被关注者ID查询关注的人列表
     * @param followee_id 被关注者ID
     * @return {@link List<UserBean>} 关注者列表
     */
    @Override
    public List<UserBean> getFollower(int followee_id) {
        List<SubBean> SQLGet =  userSubscriptionMapper.getUserSubscriptionByFollowee(followee_id);
        List<UserBean> result = new ArrayList<>();

        for (SubBean subBean : SQLGet) {
            UserBean userBean = userMapper.getFromId(subBean.getFollower());
            result.add(userBean);
        }

        return result;
    }


    @Override
    public List<UserBean> getSubFromId(int id) {
        SubBean subBean = userSubscriptionMapper.getSubById(id);
        List<UserBean> result = new ArrayList<>();

        result.add(userMapper.getFromId(subBean.getFollower()));
        result.add(userMapper.getFromId(subBean.getFollowee()));

        return result;
    }
}
