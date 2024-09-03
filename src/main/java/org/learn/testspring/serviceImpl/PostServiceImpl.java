package org.learn.testspring.serviceImpl;

import org.learn.testspring.bean.PostBean;
import org.learn.testspring.controller.ApiController;
import org.learn.testspring.mapper.PostMapper;
import org.learn.testspring.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    private final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public ApiController.PostResponse getPostList(int pageNo, int pageSize) {
        // 处理应该获取的信息，进行返回
        int total = postMapper.countPosts();

        List<PostBean> postBeans = postMapper.getTopsPage(pageSize, pageSize* (pageNo-1));
        if (postBeans.size() == pageSize) { return new ApiController.PostResponse(postBeans, total) ; }
        else {
            int num = pageSize - postBeans.size();
            postBeans.addAll(postMapper.getNormalPage(num, pageSize * (pageNo-1)));
        }
        return new ApiController.PostResponse(postBeans, total);
    }


    @Override
    public int createPost(PostBean postBean) {
        try {
            return postMapper.insertPost(postBean);
        } catch (Exception e) {
            logger.error("数据库保存出错",e);
            return 0;
        }
    }

    @Override
    public int addView(int post_id) {

        //TODO 检测用户是否有刷View的情况，酌情添加

        postMapper.addReadPost(post_id);

        return 0;
    }

}
