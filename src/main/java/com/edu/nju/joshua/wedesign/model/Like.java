package com.edu.nju.joshua.wedesign.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Repository
@Data
public class Like {
    public Integer id;
    public Integer userId;
    public Integer workId;
}
