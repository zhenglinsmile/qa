package top.fine.qa.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String tag;

    private Long author;

    private Integer readCnt;

    private Integer commentCnt;

    private Integer likeCnt;

    private Integer unLikeCnt;

    private Long createTime;

    private Long modifyTime;
}
