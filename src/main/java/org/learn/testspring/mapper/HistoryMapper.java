package org.learn.testspring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.learn.testspring.bean.HistoryBean;

import java.util.List;

public interface HistoryMapper {

    /**
     * 获取About中的更新面板
     * @return {@link HistoryBean}
     */
    @Select("SELECT * from update_history ORDER BY id DESC ")
    List<HistoryBean> updateHistory();
    @Insert("INSERT INTO update_history(version, title, detail, time, done) VALUES (#{version}, #{title}, #{detail}, #{time}, '0')")
    int insertHistory(HistoryBean historyBean);

}
