package org.learn.testspring.service;

import org.learn.testspring.bean.PostBean;
import org.learn.testspring.controller.ApiController;

import java.util.List;

public interface PostService {
    /**
     * 获取页面信息列表
     * @param pageNo 页码
     * @param pageSize 页面数据量
     * @return {@link List<PostBean>} 信息列表
     */
    ApiController.PostResponse getPostList(int pageNo, int pageSize);

    /**
     * 发布帖子
     * @param postBean
     * @return
     */
    int createPost(PostBean postBean);

    /**
     * 增加帖子阅读量数字
     * @param post_id 帖子id
     * @return int 还没想好是干啥的
     */
    int addView(int post_id);
}
