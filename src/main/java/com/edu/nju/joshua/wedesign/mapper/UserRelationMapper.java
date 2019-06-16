package com.edu.nju.joshua.wedesign.mapper;

import com.edu.nju.joshua.wedesign.model.UserRelation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserRelationMapper {
    @Select("select * from userrelation where userIdA=#{idA} and userIdB=#{idB}")
    public UserRelation getUserRelation(Integer idA, Integer idB);
    @Insert("insert into userrelation(userIdA,userIdB,relationStatus,relationStart) values(#{userIdA},#{userIdB},#{relationStatus},#{relationStart})")
    public void addUserRelation(UserRelation userRelation);
    @Delete("delete from userrelation where userIdA=#{idA} and userIdB=#{idB}")
    public boolean deleteUserRelation(Integer idA,Integer idB);
    @Update("update userrelation set relationStatus=#{relationStatus},relationStart=#{relationStart} where userIdA=#{userIdA},userIdB=#{userIdB} ")
    public boolean updateUser(UserRelation userRelation);
    @Select("select * from userrelation where userIdA=#{id} or userIdB=#{id}")
    public List<UserRelation> getAllFriends(Integer id);
}
