package com.seu.drunkdog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TagMapper {
    @Insert("insert into tag values(null, #{category})")
    void saveCategory(@Param("category") String category);
    @Select("select ifnull((select id from tag where category = #{category}), 0) as id")
    int searchIdByCategory(@Param("category")String category);
}
