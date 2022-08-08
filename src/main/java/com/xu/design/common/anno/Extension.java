package com.xu.design.common.anno;

import java.lang.annotation.*;

/**
 * 扩展点注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Extension {
}
