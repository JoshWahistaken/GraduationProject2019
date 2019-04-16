//package com.edu.nju.joshua.wedesign.model;
//
//import com.alibaba.fastjson.JSON;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.stereotype.Repository;
//
///**
// * WebSocket 聊天消息类
// */
//@Repository
//@AllArgsConstructor
//@Data
//public class Message {
//
//    public static final String ENTER = "ENTER";
//    public static final String SPEAK = "SPEAK";
//    public static final String QUIT = "QUIT";
//
//    private String type;//消息类型
//
//    private Integer fromUserId; //发送人
//
//    private String msg; //发送消息
//
//    private Integer toUserId;
//
//
//    public static String jsonStr(String type, Integer fromUserId, String msg, Integer toUserId) {
//        return JSON.toJSONString(new Message(type, fromUserId, msg, toUserId));
//    }
//
//}
