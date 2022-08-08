package com.xu.design.utils;

import java.util.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

public class CommonUtils {
    /**
     * 是否为空
     *
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return StrUtil.isEmpty((CharSequence) o);
        }
        if (o.getClass().isArray()) {
            return ArrayUtil.isEmpty(o);
        }
        if (o instanceof Collection) {
            return CollUtil.isEmpty((Collection) o);
        }
        if (o instanceof Map) {
            return CollUtil.isEmpty((Map) o);
        }
        if (o instanceof Iterable) {
            return CollUtil.isEmpty((Iterable) o);
        }
        if (o instanceof Iterator) {
            return CollUtil.isEmpty((Iterator) o);
        }
        if (o instanceof Enumeration) {
            return CollUtil.isEmpty((Enumeration) o);
        }
        return false;
    }

    /**
     * 是否不为空
     *
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }

    /**
     * 判断是否相等
     *
     * @param o1
     * @param o2
     * @return
     */
    public static boolean isEquals(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }

    /**
     * 判断是否不相等
     *
     * @param o1
     * @param o2
     * @return
     */
    public static boolean isNotEquals(Object o1, Object o2) {
        return !isEquals(o1, o2);
    }

    /**
     * 转成 boolean，null => false
     *
     * @param o
     * @return
     */
    public static boolean toBool(Object o) {
        return o != null && toBoolean(o);
    }

    /**
     * 转成 Boolean，null => null
     *
     * @param o
     * @return
     */
    public static Boolean toBoolean(Object o) {
        return Convert.convert(Boolean.class, o, null);
    }


    /**
     * 转成 Integer，null => null
     *
     * @param o
     * @return
     */
    public static Integer toInt(Object o) {
        return Convert.convert(Integer.class, o, null);
    }

    /**
     * 转成 Long，null => null
     *
     * @param o
     * @return
     */
    public static Long toLong(Object o) {
        return Convert.convert(Long.class, o, null);
    }

    /**
     * 转成 String，null => null
     *
     * @param o
     * @return
     */
    public static String toString(Object o) {
        return Optional.ofNullable(o).map(Object::toString).orElse(null);
    }


    /**
     * uuid
     *
     * @return
     */
    public static String UUID() {
        return cn.hutool.core.lang.UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return
     */
    public static String simpleUUID() {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 拼接 string
     *
     * @param separator
     * @param pieces
     * @return
     */
    public static String join(String separator, Object... pieces) {
        StringBuilder result = new StringBuilder();
        separator = separator != null ? separator : "";
        for (int i = 0; i < pieces.length; i++) {
            Object piece = pieces[i];
            if (isNotEmpty(piece)) {
                if (i != 0) {
                    result.append(separator);
                }
                result.append(piece);
            }
        }
        return result.toString();
    }
}
