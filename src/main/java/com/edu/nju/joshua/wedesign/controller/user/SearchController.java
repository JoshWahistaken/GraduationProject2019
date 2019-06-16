package com.edu.nju.joshua.wedesign.controller.user;

import com.edu.nju.joshua.wedesign.mapper.WorkMapper;
import com.edu.nju.joshua.wedesign.model.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    WorkMapper workMapper;
    @GetMapping("/search")
    public String searchByKeyword(Model model, @RequestParam("keyword") String keyword, HttpSession session, @RequestParam(value = "kind",required = false) String kind, HttpServletRequest request, HttpServletResponse response){
        session.setAttribute("keyword",keyword);
        keyword='%'+keyword+'%';
        List<Work> matchTitle=workMapper.getWorkByKeywordMatchTitle(keyword);
        List<Work> matchingTitle=new ArrayList<>();
        List<Work> matchingDescription=new ArrayList<>();
        for(Work w:matchTitle){
            boolean b= false;
            if(kind!=null){
                if(!w.kind.equals(kind)){
                    b=true;
                    break;
                }
            }
            for(Work wt:matchingTitle){
                if(w.serialId.equals(wt.serialId)){
                   b=true;
                   break;
                }
            }
            if(!b){
                matchingTitle.add(w);
            }
        }
        List<Work> matchDescription=workMapper.getWorkByKeywordMatchDescription(keyword);
        for(Work w:matchDescription){
            boolean b= false;
            if(kind!=null){
                if(!w.kind.equals(kind)){
                    b=true;
                    break;
                }
            }
            for(Work wt:matchingDescription){
                if(w.serialId.equals(wt.serialId)){
                    b=true;
                    break;
                }
            }
            if(!b){
                matchingDescription.add(w);
            }
        }
        model.addAttribute("matchingTitle",matchingTitle);
        model.addAttribute("matchingDescription",matchingDescription);
        return "user/search";
    }
}
