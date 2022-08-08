package com.xu.design.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "design_user")
@Data
public class DesignUserDO extends BaseDO {

    private String name;
    private String phoneNo;
}
