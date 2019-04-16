package com.edu.nju.joshua.wedesign.mapper;

import com.edu.nju.joshua.wedesign.model.Like;
import com.edu.nju.joshua.wedesign.model.User;
import com.edu.nju.joshua.wedesign.model.Work;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface LikeMapper {
    @Select("select * from likes where userId=#{userId} and workId=#{workId}")
    public Like getByUserIdAndWorkId(Integer userId,Integer workId);
    @Insert("insert into likes(userId,workId) values(#{userId},#{workId})")
    public Integer like(Integer userId,Integer workId);
    @Delete("delete from likes where userId=#{userId} and workId=#{workId}")
    public Integer unLike(Integer userId,Integer workId);

}
