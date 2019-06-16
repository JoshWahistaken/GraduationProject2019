package com.edu.nju.joshua.wedesign.mapper;

import com.edu.nju.joshua.wedesign.model.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagMapper {
    @Insert("insert into tag(tagName)values(#{tagName})")
    public int addTag(String tagName);
    @Select("select * from tag where tagName=#{tagName}")
    public List<Tag> getTagByName(String tagName);
    @Select("select * from tag where tagId=#{tagId}")
    public int getTagById(Integer tagId);
}
