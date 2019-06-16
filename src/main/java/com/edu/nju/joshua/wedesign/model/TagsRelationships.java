package com.edu.nju.joshua.wedesign.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Repository
@Data
public class TagsRelationships {
    public Integer id;
    public Integer tagId;
    public Integer workId;
}
