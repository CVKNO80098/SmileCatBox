package org.learn.testspring.controller;

import lombok.Data;
import org.learn.testspring.bean.HistoryBean;
import org.learn.testspring.bean.PostBean;
import org.learn.testspring.bean.UserBean;
import org.learn.testspring.config.AppConstants;
import org.learn.testspring.dto.postIdDto;
import org.learn.testspring.mapper.HistoryMapper;

import org.learn.testspring.mapper.CarouselMapper;
import org.learn.testspring.service.CookiesService;
import org.learn.testspring.service.PostService;
import org.learn.testspring.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * 集成了前端调用的主要API
 * @author 泠水 CVKNO80098@outlook.com
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    private final PostService postService;

    private final HistoryMapper historyMapper;

    private final CookiesService cookiesService;

    private final UserService userService;

    private final CarouselMapper carouselMapper;

    @Autowired
    public ApiController(HistoryMapper historyMapper, CookiesService cookiesService, UserService userService, PostService postService, CarouselMapper carouselMapper) {
        this.historyMapper = historyMapper;
        this.cookiesService = cookiesService;
        this.userService = userService;
        this.postService = postService;
        this.carouselMapper = carouselMapper;
    }

    /**
     * 获取轮滚图显示列表
     * @return {@link List} 返回列表
     */
    @RequestMapping("/Carousel")
    public List<CarouselResponse> Carousel () {
        return carouselMapper.getList();
    }
    @Data
    public static class CarouselResponse {
        private int id;
        private String img;
        private String text;
        private String author;
    }

    /**
     * 获取POST列表页面信息
     * @param i 第几页啦~
     * @param cookie 你是谁啊~
     * @return {@link List<PostBean>} 一个帖子列表，长度见参阅
     * @see AppConstants
     */
    @RequestMapping("/posts/{i}")
    public PostResponse post(@PathVariable int i, @CookieValue(name = "cookie", defaultValue = "None")String cookie) {
        // 确认登录状态
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return null;
        }
        // 处理应该获取的信息，进行返回
        return postService.getPostList(i, AppConstants.PAGE_SIZE);
    }
    @Data
    public static class PostResponse  {
        private List<PostBean> posts;
        private int total;

        public PostResponse(List<PostBean> posts, int total) {
            this.posts = posts;
            this.total = total;
        }
    }

    /**
     * 增加POST的访问量
     * @param cookie 用户默认传参
     * @param request 指定id
     * @return {@link Boolean}
     */
    @PostMapping("/read_post")
    public ResponseEntity<Boolean> readPost(@CookieValue(name = "cookie", defaultValue = "None") String cookie,
                                            @RequestBody postIdDto request) {
        UserBean userBean = cookiesService.getCookies(cookie);

        if (userBean == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        // 返回 true 即使在异常情况下
        if (postService.addView(request.getPost_id()) <= 0) {
            logger.warn("用户 {} (ID: {}) 疑似刷View，请管理员核实", userBean.getUsername(), userBean.getId());
        }
        return ResponseEntity.ok(true);
    }


    /**
     * 发布一个新的帖子！
     * @param postBean 这个帖子的详细数据
     * @param cookie 身份验证
     * @return {@link PostBean} 返回你输入的信息，如果为null则失败
     */
    @RequestMapping("/new_post")
    public PostBean newPost(@RequestBody PostBean postBean, @CookieValue(name = "cookie", defaultValue = "None")String cookie) {
        // 确认登录状态
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return null;
        }
        int author = userBean.getId();

        postBean.setOverhead(0);
        postBean.setAuthor(author);
        if (postBean.getTarget_date() == null) postBean.setTarget_date(null);
        if (postBean.getCover() == null) postBean.setCover(null);
        if (postBean.getPrivacy() == null) postBean.setPrivacy("0");

        if (postService.createPost(postBean) < 1) {
            return null;
        }
        return postBean;
    }

    /***
     * 获取历史更新的API
     * @apiNote 必须传入Cookie
     * @param cookie 登录获取
     * @return {@link List<HistoryBean>}
     */
    @RequestMapping("/history")
    public List<HistoryBean> getHistory(@CookieValue(name = "cookie", defaultValue = "None") String cookie) {
        UserBean userBean = cookiesService.getCookies(cookie);
        if (userBean == null) {
            return null;
        }

        List<HistoryBean> historyBeans;
        try {
            historyBeans = historyMapper.updateHistory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return historyBeans;
    }

    /***
     * 上传文件的函数
     * @param file 上传一个文件
     * @return {@link UploadResponse} 一个图像链接和信息
     */
    @PostMapping("/upload-image")
    public UploadResponse uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        File uploadDir = new File(AppConstants.UPLOAD_DIR);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        try {
            Files.copy(file.getInputStream(), Paths.get(AppConstants.UPLOAD_DIR, fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("An error occurred",e);
            throw new RuntimeException("Failed to store file.");
        }

        // 构造返回的图片 URL，这里假设你的服务在 http://localhost:8080
        String fileUrl = "http://localhost:8080/api/" + AppConstants.UPLOAD_DIR + fileName;

        // 返回响应
        return new UploadResponse(fileUrl, "描述信息");
    }
    @Data
    public static class UploadResponse {
        private String url;
        private String desc;

        public UploadResponse(String url, String desc) {
            this.url = url;
            this.desc = desc;
        }
    }
}
