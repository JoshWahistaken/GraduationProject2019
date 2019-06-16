package com.edu.nju.joshua.wedesign.mapper;

import com.edu.nju.joshua.wedesign.model.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MessageMapper {
    @Select("select * from message where fromUserId=#{fromUserId}")
    public List<Message> getMessageByFromId(Integer fromUserId);
    @Select("select * from message where toUserId=#{toUserId}")
    public List<Message> getMessageByToId(Integer toUserId);
    @Select("select * from message where toUserId=#{toUserId} and isTransport=0")
    public List<Message> getMessageUnReceive(Integer toUserId);
    @Select("select * from message where messageType=#{messageType}")
    public List<Message> getMessageByType(Integer messageType);
    @Insert("insert into message(fromUserId,toUserId,messageType,time,content,isTransport) values(#{fromUserId},#{toUserId},#{messageType},#{time},#{content},#{isTransport})")
    public int addMessage(Message message);
    @Delete("delete from message where id=#{id}")
    public int deleteMessage(Message message);
    @Update("update message set isTransport=#{isTransport} where id=#{id}")
    public int updateMessage(Message message);
}
