package org.learn.testspring.serviceImpl;

import org.learn.testspring.bean.NoticeBean;
import org.learn.testspring.mapper.NoticeMapper;
import org.learn.testspring.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Override
    public List<NoticeBean> getNoticeLs(int user_id) {
        List<NoticeBean> result = noticeMapper.selectAll(user_id);
        result.addAll(noticeMapper.selectAll(0));

        result.sort(Comparator.comparing((NoticeBean notice) -> Boolean.parseBoolean(notice.getIs_read()))
                .thenComparing(NoticeBean::getId, Comparator.reverseOrder()));

        return result;
    }

    @Override
    public Boolean readNotice(int user_id, int notice_id) {
        return noticeMapper.read(notice_id, user_id) > 0;
    }
}
