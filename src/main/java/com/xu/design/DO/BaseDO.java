package com.xu.design.DO;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDO {
    private Long id;
    private Integer deleted;
    private Date createdAt;
    private Date updatedAt;
}
