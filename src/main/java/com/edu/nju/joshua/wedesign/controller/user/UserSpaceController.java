package com.edu.nju.joshua.wedesign.controller.user;

import com.edu.nju.joshua.wedesign.mapper.TagMapper;
import com.edu.nju.joshua.wedesign.mapper.TagsRelationshipsMapper;
import com.edu.nju.joshua.wedesign.mapper.UserMapper;
import com.edu.nju.joshua.wedesign.mapper.WorkMapper;
import com.edu.nju.joshua.wedesign.model.Tag;
import com.edu.nju.joshua.wedesign.model.TagsRelationships;
import com.edu.nju.joshua.wedesign.model.User;
import com.edu.nju.joshua.wedesign.model.Work;
import com.edu.nju.joshua.wedesign.service.UserService;
import com.edu.nju.joshua.wedesign.utils.Enums.ChangeInfoResult;
import com.edu.nju.joshua.wedesign.utils.Enums.RegisterResult;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.sun.deploy.net.HttpResponse;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class UserSpaceController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    User user;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagsRelationshipsMapper tagsRelationshipsMapper;

    @GetMapping("/space")
    public String getUserSpace(Model model, @ModelAttribute(value = "user") User user, @ModelAttribute(value = "work") Work work, @RequestParam(value = "id", required = false) Integer id, HttpSession session) {
        if (id != null) {
            model.addAttribute("user", userMapper.getUserById(id));
            List<Work> userWorks = workMapper.getByUserId(id);
            List<Work> userWorksFirstPic = new ArrayList<>();
            for (Work w : userWorks) {
                if (w.childrenId == 1) {
                    userWorksFirstPic.add(w);
                }
            }
            model.addAttribute("works", userWorksFirstPic);
            return "user/userSpace";

        } else {
            User currentUser = (User) session.getAttribute("user");
            id = currentUser.getId();
            model.addAttribute("user", userMapper.getUserById(id));
            List<Work> userWorks = workMapper.getByUserId(id);
            List<Work> userWorksFirstPic = new ArrayList<>();
            for (Work w : userWorks) {
                if (w.childrenId == 1) {
                    userWorksFirstPic.add(w);
                }
            }
            model.addAttribute("works", userWorksFirstPic);
            return "user/userSpace";
        }
    }

    @RequestMapping(value = "/userinfo/edit", method = RequestMethod.POST)
    public void processInfoForm(User user, Model model, HttpSession session, HttpServletRequest request, RedirectAttributes redir, HttpServletResponse response) throws Exception {
        User currentUser = (User) session.getAttribute("user");
        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure(user.getPassword());
//        redir.addAttribute("anchor",4);
        if (strength.getScore() < 3) {
//            redir.addFlashAttribute("warningMessage", "Your password is too weak.  Choose a stronger one.");
//            return "redirect:/space";
            response.getWriter().write("<script>alert('Your password is too weak.  Choose a stronger one.');location = '/wedesign/space';</script>");
//            return "redirect:/space";
        } else {
            currentUser.setName(user.getName());
            currentUser.setDescription(user.getDescription());
            currentUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userMapper.updateUser(currentUser);
            session.setAttribute("user", currentUser);

//            redir.addFlashAttribute("successMessage", "Your password has been reset successfully!");
//            return "redirect:/space";
            response.getWriter().write("<script>alert('Your info has been updated successfully!');location = '/wedesign/space';</script>");
//            return "redirect:/space";
        }


    }

    @PostMapping("/avatar/edit")
    public void avatarUpload(@RequestParam("avatar1") MultipartFile fileUpload, User user, Model model, HttpSession session, HttpServletRequest request, RedirectAttributes redir, HttpServletResponse response) throws Exception {
        User currentUser = (User) session.getAttribute("user");
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID() + suffixName;
        //指定本地文件夹存储图片
        String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/img/";
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath + fileName));
            currentUser.setAvatar("static/img/" + fileName);
            currentUser.setAvatar_x(user.getAvatar_x());
            currentUser.setAvatar_y(user.getAvatar_y());
            currentUser.setAvatar_w(user.getAvatar_w());
            currentUser.setAvatar_h(user.getAvatar_h());
            userMapper.updateUser(currentUser);
            response.getWriter().write("<script>alert('Your Avatar is successfully updated!');location = '/wedesign/space';</script>");
        } catch (Exception e) {
            response.getWriter().write("<script>alert('Something went wrong, fix it man..');location = '/wedesign/space';</script>");
            e.printStackTrace();
        }
    }

    @PostMapping("/user/upload")
    public void workUpload(Model model,@RequestParam("tagsinput")String tags, @RequestParam("file") MultipartFile[] file, Work work, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        if(!tags.equals("")){
//            String[] tag=tags.split(",");
//            int[] tagIds=new int[tag.length];
//            for(int i=0;i<tag.length;i++){
//                List<Tag> tagCheck=tagMapper.getTagByName(tag[i]);
//                if(tagCheck.size()==0) {
//                    tagMapper.addTag(tag[i]);
//                    tagIds[i]=tagMapper.getTagByName(tag[i]).get(0).id;
//                }
//                else{
//                    tagIds[i]=tagCheck.get(0).id;
//                }
//            }
//        }
        int[] tagIds=new int[0];
        if (file.length > 0) {
            if(!tags.equals("")){
                String[] tag=tags.split(",");
                tagIds=new int[tag.length];
                for(int i=0;i<tag.length;i++){
                    List<Tag> tagCheck=tagMapper.getTagByName(tag[i]);
                    if(tagCheck.size()==0) {
                        tagMapper.addTag(tag[i]);
                        tagIds[i]=tagMapper.getTagByName(tag[i]).get(0).id;
                    }
                    else{
                        tagIds[i]=tagCheck.get(0).id;
                    }
                }
            }
            String serialId = UUID.randomUUID() + "";
            int i = 1;
            for (MultipartFile f : file) {
                String fileName = f.getOriginalFilename();
                //获取文件后缀名
                assert fileName != null;
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                //指定本地文件夹存储图片
                String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/img/works/";
                Integer childrenId = i;
                try {
                    f.transferTo(new File(filePath + fileName));
                    work.setSerialId(serialId);
                    work.setChildrenId(i);
                    User currentUser = (User) session.getAttribute("user");
                    work.setUserId(currentUser.getId());
                    i += 1;
                    work.setImgPath("static/img/works/" + fileName);
                    workMapper.addWork(work);
                } catch (Exception e) {
                    response.getWriter().write("<script>alert('Something went wrong, fix it man..');location = '/wedesign/space';</script>");
                    e.printStackTrace();
                }
            }
            List<Work> worksUploaded=workMapper.getBySerialId(serialId);
            if(tagIds.length>0) {
                for (Work w : worksUploaded) {
                    for(int j=0;j<tagIds.length;j++) {
                        tagsRelationshipsMapper.addRelationship(w.id, tagIds[j]);
                    }
                }
            }
            response.getWriter().write("<script>alert('Your work is successfully uploaded!');location = '/wedesign/space';</script>");
        }
    }
}



