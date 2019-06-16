package com.edu.nju.joshua.wedesign.model;

import com.edu.nju.joshua.wedesign.mapper.WorkMapper;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Data
public class Work{
    public Integer id;
    public Integer userId;
    public String serialId;
    public String imgPath;
    public Integer childrenId;
    public Date date;
    public String title;
    public String description;
    public Integer likes;
    public String kind;
}
