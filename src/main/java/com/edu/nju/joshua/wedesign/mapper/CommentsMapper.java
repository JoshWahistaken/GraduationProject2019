package com.edu.nju.joshua.wedesign.mapper;

import com.edu.nju.joshua.wedesign.model.Comments;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentsMapper {
    @Select("select * from comments where workId=#{workId}")
    public List<Comments> getCommentsByWorkId(Integer workId);
    @Select("select * from comments where parentId=#{parentId}")
    public List<Comments> getCommentsByParentId(Integer parentId);
    @Select("select * from comments where childrenId=#{childrenId}")
    public List<Comments> getCommentsByChildrenId(Integer childrenId);
    @Select("select * from comments where userId=#{userId}")
    public List<Comments> getCommentsByUserId(Integer userId);
    @Insert("insert into comments(parentId, childrenId, workId, userId, date, content, username) values(#{parentId}, #{childrenId}, #{workId}, #{userId}, #{date}, #{content}, #{username})")
    public int addComments(Comments comments);
    @Delete("delete from comments where id=#{id}")
    public int deleteComments(Integer id);
}
