package com.edu.nju.joshua.wedesign.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
@Repository
@Data
public class UserRelation {
    private Integer userIdA;
    private Integer userIdB;
    private Integer relationStatus;
    private Timestamp relationStart;
}
