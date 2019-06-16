package com.edu.nju.joshua.wedesign.model;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * WebSocket 聊天消息类
 */
@Repository
@Data
public class Message {

    public Message() {
    }

    private Integer id;
    private Integer fromUserId;
    private Integer toUserId;
    private String content;
    private Integer messageType;
    private Timestamp time;
    private Integer isTransport;

}
