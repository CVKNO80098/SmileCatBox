package org.learn.testspring.serviceImpl;

import org.learn.testspring.bean.PostBean;
import org.learn.testspring.bean.StarBean;
import org.learn.testspring.bean.UserBean;
import org.learn.testspring.mapper.PostMapper;
import org.learn.testspring.mapper.StarMapper;
import org.learn.testspring.mapper.UserMapper;
import org.learn.testspring.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StarServiceImpl implements StarService {

    private final StarMapper starMapper;
    private final UserMapper userMapper;
    private final PostMapper postMapper;

    @Autowired
    public StarServiceImpl(StarMapper starMapper, UserMapper userMapper, PostMapper postMapper) {
        this.starMapper = starMapper;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
    }

    @Override
    public Boolean createStar(int user_id, int post_id) {

        if (starMapper.createStar(user_id, post_id) > 0) {
            postMapper.RefreshStarNum(post_id, starMapper.getCountStar("post_id", post_id));
            return true;
        }

        return false;
    }


    @Override
    public Boolean deleteStar(int user_id, int post_id) {
        if (starMapper.deleteStar(user_id, post_id) > 0) {
            postMapper.RefreshStarNum(post_id, starMapper.getCountStar("post_id", post_id));
            return true;
        }
        return false;
    }


    @Override
    public List<PostBean> getStarPostByUser(int user_id) {
        List<PostBean> result = new ArrayList<>();
        List<StarBean> stars = starMapper.getStarByUser(user_id);

        for (StarBean star : stars) {
            result.add(postMapper.getPostById(star.getPost_id()));
        }

        return result;
    }


    @Override
    public List<UserBean> getStarUserByPost(int post_id) {
        List<UserBean> result = new ArrayList<>();
        List<StarBean> stars = starMapper.getStarByPost(post_id);

        for (StarBean star : stars) {
            result.add(userMapper.getFromId(star.getUser_id()));
        }

        return result;
    }

    @Override
    public Boolean isStar(int user_id, int post_id) {
        return starMapper.isStar(user_id, post_id) > 0;
    }
}
