package org.learn.testspring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.learn.testspring.bean.PostBean;

import java.util.List;

//这个是DAO接口文件，用XML实现
public interface PostMapper {

    /**
     *
     */
    @Select("SELECT * FROM post WHERE id=#{id}")
    PostBean getPostById(@Param("id") int id);

    /***
     * 获取一定长度（应该是20）个，ID号从大到小依次的PostBean
     *
     * @param limit 要返回的个数
     * @param offset 开始的数字
     *
     * @return {@link List<PostBean>} 一个PostBean列表
     */
    @Select("SELECT * FROM post WHERE overhead = 0 ORDER BY id DESC LIMIT #{limit} OFFSET #{offset}")
    List<PostBean> getNormalPage(@Param("limit") int limit, @Param("offset") int offset);

    /***
     * 用于增加一个post
     *
     * @param postBean 传入一个post内容
     * @return {@link int} 操作成功的数量
     */
    @Insert("INSERT INTO post(overhead, title, content, time, author, target_date, cover, visits, star_num, type, privacy) VALUES " +
            "(0, #{title}, #{content}, #{time}, #{author}, #{target_date, jdbcType=TIMESTAMP}, #{cover, jdbcType=VARCHAR}," +
            "#{visits}, #{star_num}, #{type}, #{privacy})")
    int insertPost(PostBean postBean);

    /***
     * 获取被顶置的Post
     * @param limit 要返回的个数
     * @param offset 开始的数字
     * @return {@link List} 搜索到的列表
     */
    @Select("SELECT * FROM post WHERE overhead = 1 ORDER BY id DESC LIMIT #{limit} OFFSET #{offset}")
    List<PostBean> getTopsPage(@Param("limit") int limit, @Param("offset") int offset);

    /***
     * 获取帖子总数
     * @return 数量
     */
    @Select("SELECT COUNT(*) FROM post")
    int countPosts();

    /**
     * 增加阅读量
     * @param post_id 对应的PostID
     */
    @Update("UPDATE post SET `visits` = `visits`+1 WHERE id=#{post_id}")
    int addReadPost(int post_id);

    @Update("UPDATE post SET `star_num` = #{star_num} WHERE id=#{post_id}")
    int RefreshStarNum(@Param("post_id") int post_id, @Param("star_num") int star_num);
}
