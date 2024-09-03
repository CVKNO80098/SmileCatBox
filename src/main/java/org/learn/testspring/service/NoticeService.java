package org.learn.testspring.service;

import org.learn.testspring.bean.NoticeBean;

import java.util.List;

public interface NoticeService {

    /**
     * 获取用户通知列表
     * @param user_id 用户id
     * @return {@link List<NoticeBean>} 信息列表
     */
    List<NoticeBean> getNoticeLs(int user_id);

    /**
     * 标记为已读
     * @param user_id 用户id
     * @param notice_id 通知id
     * @return {@link Boolean} 是否成功
     */
    Boolean readNotice(int user_id, int notice_id);
}
