package com.edu.nju.joshua.wedesign.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.nju.joshua.wedesign.mapper.CommentsMapper;
import com.edu.nju.joshua.wedesign.mapper.LikeMapper;
import com.edu.nju.joshua.wedesign.mapper.UserMapper;
import com.edu.nju.joshua.wedesign.mapper.WorkMapper;
import com.edu.nju.joshua.wedesign.model.Comments;
import com.edu.nju.joshua.wedesign.model.Like;
import com.edu.nju.joshua.wedesign.model.User;
import com.edu.nju.joshua.wedesign.model.Work;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Controller
public class WorkController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    WorkMapper workMapper;
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    CommentsMapper commentsMapper;
    @GetMapping("/work")
    public String showWorkPage(Model model, HttpSession session, @ModelAttribute(value="comments") Comments comments, @ModelAttribute(value="work") Work work,@ModelAttribute(value = "user") User user, HttpServletRequest request, @RequestParam(value="workId", required=false) Integer workId) {
        boolean liked = false;
        if (workId != null) {
            model.addAttribute("work", workMapper.getById(workId));
            List<Work> workDetail = new ArrayList<>();
            workDetail = workMapper.getBySerialId(workMapper.getById(workId).serialId);
            model.addAttribute("workDetial", workDetail);
            List<Comments> parentComments=new ArrayList<>();
            List<Comments> childrenComments=new ArrayList<>();
            List<Comments> allComments=commentsMapper.getCommentsByWorkId(workId);
            for(Comments c: allComments){
                if(c.parentId!=0){
                    childrenComments.add(c);
                }else{
                    parentComments.add(c);
                }
            }
            model.addAttribute("parentComments",parentComments);
            model.addAttribute("childrenComments",childrenComments);

        }
        User currentUser = (User) session.getAttribute("user");
        model.addAttribute("user", currentUser);
        Integer userId = currentUser.getId();
        Like like = likeMapper.getByUserIdAndWorkId(currentUser.getId(), workId);
        if (like != null) {
            liked = true;
        }
        model.addAttribute("liked", liked);

        return "work";
    }
    @PostMapping("work/ajaxComment")
    public void addComments(@RequestBody Comments comments, HttpServletResponse response) throws IOException {
        comments.username=userMapper.getUserById(comments.userId).getName();
        Integer result=commentsMapper.addComments(comments);
        String flagMessage = "success";
        String hintMessage="评论成功！！！";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",flagMessage);
        jsonObject.put("hintMessage",hintMessage);
        System.out.println(jsonObject.toJSONString());
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(jsonObject.toJSONString());
        response.getWriter().close();

    }
    @PostMapping("work/ajaxDeleteComment")
    public void deleteComments(@RequestBody Comments comments, HttpServletResponse response) throws IOException{
        Integer result=commentsMapper.deleteComments(comments.id);
        String flagMessage = "success";
        String hintMessage="删除评论成功！！！";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",flagMessage);
        jsonObject.put("hintMessage",hintMessage);
        System.out.println(jsonObject.toJSONString());
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(jsonObject.toJSONString());
        response.getWriter().close();

    }
    @GetMapping("/workImg")
    public String showWorkImgPage(Model model, HttpSession session, @ModelAttribute(value="work") Work work,@ModelAttribute(value = "user") User user, HttpServletRequest request, @RequestParam(value="workId", required=false) Integer workId){
        boolean liked=false;
        if(workId!=null){
            model.addAttribute("work",workMapper.getById(workId));
            List<Work> workDetail=new ArrayList<>();
            workDetail=workMapper.getBySerialId(workMapper.getById(workId).serialId);
            model.addAttribute("workDetial",workDetail);


        }
        User currentUser=(User)session.getAttribute("user");
        model.addAttribute("user",currentUser);
        Integer userId=currentUser.getId();
        Like like= likeMapper.getByUserIdAndWorkId(currentUser.getId(),workId);
        if(like!=null){
            liked=true;
        }
        model.addAttribute("liked",liked);

            return "workImg";
    }
    @PostMapping("work/ajaxlike")
    public void ajaxLike(@RequestBody Like like, HttpServletResponse response) throws IOException {
        Integer userId= like.getUserId();
        Integer workId= like.getWorkId();
        Integer dataLike=likeMapper.like(userId,workId);
        Work work=workMapper.getById(workId);
        work.likes+=1;
        workMapper.updateWork(work);
        String flagMessage = "success";
        String hintMessage="喜欢成功!!!";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",flagMessage);
        jsonObject.put("hintMessage",hintMessage);
        System.out.println(jsonObject.toJSONString());
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(jsonObject.toJSONString());
        response.getWriter().close();
    }
    @PostMapping("work/ajaxUnlike")
    public void ajaxUnLike(@RequestBody Like like, HttpServletResponse response) throws IOException {
        Integer userId= like.getUserId();
        Integer workId= like.getWorkId();
        Integer dataLike=likeMapper.unLike(userId,workId);
        Work work=workMapper.getById(workId);
        work.likes-=1;
        workMapper.updateWork(work);
        String flagMessage = "success";
        String hintMessage="取消喜欢成功!!!";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag",flagMessage);
        jsonObject.put("hintMessage",hintMessage);
        System.out.println(jsonObject.toJSONString());
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(jsonObject.toJSONString());
        response.getWriter().close();
    }
}
