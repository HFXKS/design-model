package com.xu.design.common.anno;

import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 扩展注入注解
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ExtensionInject {
    /**
     * 扩展点描述 code
     */
    String value();

    /**
     * 责任链排序，只有当扩展点上下文定义为责任链模式时才生效
     */
    int order() default Ordered.LOWEST_PRECEDENCE;
}
