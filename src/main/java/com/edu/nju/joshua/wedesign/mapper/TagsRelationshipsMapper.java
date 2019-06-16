package com.edu.nju.joshua.wedesign.mapper;

import com.edu.nju.joshua.wedesign.model.TagsRelationships;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagsRelationshipsMapper {
    @Select("select * from tagsRelationships where workId=#{workId}")
    public List<TagsRelationships> getRelationshipsByWorkId(Integer workId);
    @Insert("insert into tagsRelationships(workId,tagId) values(#{workId},#{tagId})")
    public int addRelationship(Integer workId,Integer tagId);
}
