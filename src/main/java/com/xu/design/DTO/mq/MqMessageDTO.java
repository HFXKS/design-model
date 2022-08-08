package com.xu.design.DTO.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqMessageDTO implements Serializable {
    private Integer id;
    private String name;
}
