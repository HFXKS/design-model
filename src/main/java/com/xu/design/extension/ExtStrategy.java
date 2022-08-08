package com.xu.design.extension;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 扩展点策略
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ExtStrategy extends BlacklistDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 归属的扩展点
     * 当为空时归属于 global
     */
    private String extension;

    /**
     * 能力
     */
    private String ability;

    /**
     * 描述
     */
    private String desc;

    public static ExtStrategy of(String extension, String ability) {
        return new ExtStrategy()
                .setExtension(extension)
                .setAbility(ability);
    }

    public static ExtStrategy of(String extension, String ability, String desc) {
        return of(extension, ability).setDesc(desc);
    }
}