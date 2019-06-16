package com.edu.nju.joshua.wedesign.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Data
public class Comments {
    public Integer id;
    public Integer parentId;
    public Integer childrenId;
    public Integer workId;
    public String content;
    public String date;
    public Integer userId;
    public String username;
}
