package com.edu.nju.joshua.wedesign.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Repository
@Data
public class Tag {
    public Integer id;
    public String tagName;
}
