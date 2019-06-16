package com.edu.nju.joshua.wedesign.mapper;

import com.edu.nju.joshua.wedesign.model.Work;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface WorkMapper {
    @Select("select * from work where id=#{id}")
    public Work getById(Integer id);
    @Select("select * from work where serialId=#{serialId}")
    public List<Work> getBySerialId(String serialId);
    @Select("select * from work where userId=#{userId}")
    public List<Work> getByUserId(Integer userId);
    @Insert("insert into work(userId, serialId, childrenId, date, imgPath, title, description,likes,kind) values(#{userId}, #{serialId}, #{childrenId}, #{date}, #{imgPath}, #{title}, #{description},0,#{kind})")
    public int addWork(Work work);
    @Delete("delete from work where serialId=#{serialId}")
    public int deleteWork(Work work);
    @Update("update work set likes=#{likes} where id=#{id}")
    public int updateWork(Work work);
    @Select("select * from work where title like #{keyword}")
    public List<Work> getWorkByKeywordMatchTitle(String keyword);
    @Select("select * from work where description like #{keyword}")
    public List<Work> getWorkByKeywordMatchDescription(String keyword);


}
