package com.xu.design.utils;

import cn.hutool.core.util.ClassUtil;
import lombok.SneakyThrows;

public class CommonClassUtils extends ClassUtil {
    private static final String DOT = ".";

    /**
     * 移除代理类拼接
     *
     * @param className
     * @return
     */
    public static String getDeclaredClassName(String className) {
        int dollarIndex = className.indexOf("$");
        if (dollarIndex > 0) {
            className = className.substring(0, dollarIndex);
        }
        return className;
    }

    /**
     * 移除代理类拼接
     *
     * @param clazz
     * @return
     */
    public static String getDeclaredClassName(Class<?> clazz) {
        if (CommonUtils.isEmpty(clazz)) {
            return null;
        }
        return getDeclaredClassName(clazz.getName());
    }

    /**
     * 移除代理类拼接
     *
     * @param o
     * @return
     */
    public static String getDeclaredClassName(Object o) {
        if (CommonUtils.isEmpty(o)) {
            return null;
        }
        return getDeclaredClassName(o.getClass());
    }

    /**
     * 获取 simpleName, 包含移除代理拼接
     * @param o
     * @return
     */
    public static String getSimpleClassName(Object o) {
        String className = getDeclaredClassName(o);
        if (CommonUtils.isNotEmpty(className)) {
            int dotIndex = className.lastIndexOf(DOT);
            if (dotIndex > 0) {
                className = className.substring(dotIndex + 1);
            }
        }
        return className;
    }

    @SneakyThrows
    public static Class<?> getDeclaredClass(Object bean) {
        Class<?> clazz = bean.getClass();
        String className = clazz.getName();
        int dollarIndex = className.indexOf("$");
        if (dollarIndex > 0) {
            className = className.substring(0, dollarIndex);
            clazz = Class.forName(className);
        }
        return clazz;
    }
}
