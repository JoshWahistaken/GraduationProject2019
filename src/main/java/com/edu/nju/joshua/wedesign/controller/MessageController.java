package com.edu.nju.joshua.wedesign.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.edu.nju.joshua.wedesign.mapper.MessageMapper;
import com.edu.nju.joshua.wedesign.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {
    @Autowired
    private MessageMapper messageMapper;
    @RequestMapping(value="/getMessageRecordBetweenUsers",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMessageRecordBetweenUsers(int userIdA, int userIdB){
        List<Message> list = messageMapper.getMessageByType(0);
        List<Message> lists = new ArrayList<Message>();
        for(int i=0;i<list.size();i++){
            Message message = list.get(i);
            if((message.getFromUserId() == userIdA && message.getToUserId() == userIdB)||(message.getFromUserId() == userIdB && message.getToUserId() == userIdA))
                lists.add(message);
        }
        String messages = JSONArray.toJSONString(lists, SerializerFeature.UseSingleQuotes);
//		System.out.println("我到了这里："+messages);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("result",messages);
        return result;
    }
    private static SerializeConfig mapping = new SerializeConfig();
    private static String dateFormat;
    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }
}
