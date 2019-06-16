package com.edu.nju.joshua.wedesign.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.edu.nju.joshua.wedesign.mapper.UserMapper;
import com.edu.nju.joshua.wedesign.mapper.UserRelationMapper;
import com.edu.nju.joshua.wedesign.model.User;
import com.edu.nju.joshua.wedesign.model.UserRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class UserRelationController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRelationMapper userRelationMapper;
    @RequestMapping(value="/buildRelation",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> buildRelation(int userIdA, int userIdB){
        UserRelation userRelation = new UserRelation();
        userRelation.setUserIdA(userIdA);
        userRelation.setUserIdB(userIdB);
        userRelation.setRelationStatus(1);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        userRelation.setRelationStart(timestamp);
        userRelationMapper.addUserRelation(userRelation);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("result", "success");
        return result;
    }

    @RequestMapping(value="/removeRelation",method= RequestMethod.POST)
    public String removeRelation(int userId, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("currentUser");
        int idA = user.getId();
        int idB = userId;
        userRelationMapper.deleteUserRelation(idA, idB);
        httpSession.setAttribute("friends", userRelationMapper.getAllFriends(user.getId()));
        return "main";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/getRelations",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getRelations(int userId){
        List<UserRelation> list = new ArrayList<UserRelation>();
        list = userRelationMapper.getAllFriends(userId);
        List<User> userList=new ArrayList<>();
        for(UserRelation ur:list){
            if (ur.getUserIdA()!=userId){
                userList.add(userMapper.getUserById(ur.getUserIdA()));
            }
            else{
                userList.add(userMapper.getUserById(ur.getUserIdB()));
            }
        }
        String relations = JSONArray.toJSONString(userList);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("relations", relations);
        return result;
    }
}
