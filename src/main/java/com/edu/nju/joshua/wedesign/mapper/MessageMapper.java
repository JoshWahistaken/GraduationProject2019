//package com.edu.nju.joshua.wedesign.mapper;
//
//import com.edu.nju.joshua.wedesign.model.Message;
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Select;
//
//public interface MessageMapper {
//    @Select("select * from message where fromUserId=#{fromUserId}")
//    public Message getMsgByFromId(Integer fromUserId);
//    @Select("select * from message where toUserId=#{toUserId}")
//    public Message getMsgByToId(Integer toUserId);
//    @Insert("insert into message(type, fromUserId, msg, toUserId) value(#{type}, #{fromUserId}, #{msg}, #{toUserId})")
//    public Message sendMsg(String type, Integer fromUserId, String msg, Integer toUserId);
//}
