package top.fine.qa.model;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * @description: description
 * @author: zhengLin
 * @version: 1.0
 * @create: 2020-04-08 15:18
 **/
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long accountId;

    private String name;

    private String token;

    private String createTime;

    private String modifyTime;

}
