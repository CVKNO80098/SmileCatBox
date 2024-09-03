package org.learn.testspring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.learn.testspring.controller.ApiController;

import java.util.List;

public interface CarouselMapper {

    @Insert("INSERT INTO Carousel_data(id, img, text, author) VALUES (#{id}, #{img}, #{text}, #{author})")
    public void insert(@Param("id") String id, @Param("img") String img, @Param("text") String text, @Param("author") String author);

    @Select("SELECT * FROM Carousel_data")
    public List<ApiController.CarouselResponse> getList();

}
