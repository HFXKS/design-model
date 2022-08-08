package com.xu.design.extension;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MessageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean success = false;

    private String code;

    private String msg;


    public static MessageDTO newSuccess() {
        return new MessageDTO().setSuccess(true);
    }
}