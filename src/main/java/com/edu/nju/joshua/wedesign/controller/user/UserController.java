package com.edu.nju.joshua.wedesign.controller.user;

import java.util.HashMap;
import java.util.Map;

import com.edu.nju.joshua.wedesign.mapper.UserMapper;
import com.edu.nju.joshua.wedesign.model.User;
import com.edu.nju.joshua.wedesign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {

    @Autowired private UserMapper userMapper;

    @RequestMapping(value="/doFindUserByName",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findUserByName(String userName){
        User user = userMapper.getUserByUsername(userName);
        //通过HashMap来构建Json数据，其实和Server里面通过JSONObject来构建效果是一样的
        Map<String,Object> result = new HashMap<String,Object>();
        if(user!=null){
            result.put("userId", user.getId());
            result.put("userName", user.getUsername());
            result.put("userNickName", user.getName());
        }
        else
            result=null;
        return result;
    };

    @RequestMapping(value="/doFindUserById",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findUserById(int userId){
        User user = userMapper.getUserById(userId);
        Map<String,Object> result = new HashMap<String,Object>();
        if(user!=null){
            result.put("userId", user.getId());
            result.put("userName", user.getUsername());
            result.put("userNickName", user.getName());
        }
        else
            result=null;
        return result;
    };


}
