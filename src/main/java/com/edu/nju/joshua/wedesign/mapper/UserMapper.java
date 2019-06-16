package com.edu.nju.joshua.wedesign.mapper;

import com.edu.nju.joshua.wedesign.model.User;
import org.apache.ibatis.annotations.*;

//@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    public User getUserById(Integer id);
    @Delete("delete from user where id=#{id}")
    public int deleteUserById(Integer id);
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into user(username, password, name, age, gender, avatar, email,confirmationToken) values(#{username}, #{password}, #{name}, #{age}, #{gender}, #{avatar}, #{email},#{confirmationToken})")
    public int insertUser(User user);
    @Update("update user set username=#{username}, password=#{password}, name=#{name}, age=#{age}, gender=#{gender}, avatar=#{avatar}, email=#{email}, enabled=#{enabled}, confirmationToken=#{confirmationToken}, description=#{description}, avatar_x=#{avatar_x}, avatar_y=#{avatar_y}, avatar_w=#{avatar_w}, avatar_h=#{avatar_h},userIsOnline=#{userIsOnline} where id=#{id}")
    public int updateUser(User user);
    @Select("select * from user where username=#{username} and password=#{password}")
    public User userAuthenticate(String username,String password);
    @Insert("insert into user(username, password, name, gender) values(#{username}, #{password}, #{name}, #{age}, #{gender}])")
    public int register(User user);
    @Select("select * from user where email=#{email}")
    public User findByEmail(String email);
    @Select("select * from user where username=#{username}")
    public User getUserByUsername(String username);
    @Select("select * from user where confirmationToken=#{confirmationToken}")
    public User findByConfirmationToken(String confirmationToken);

}
