package com.yss.ms.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @author liugang
 * @since 2018-04-08
 */
@Data
public class User implements Serializable{


    private static final long serialVersionUID = 6741489822065785659L;

    private Integer id;
    private String name;
    private Integer age;
    private Integer port;

}
