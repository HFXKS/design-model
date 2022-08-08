package com.xu.design.utils;

import java.util.Formatter;

public class CommonExceptionUtils {
    /**
     * 判空工具
     *
     * @param o
     */
    public static void checkEmpty(Object o) {
        checkEmpty(o, "can not be null");
    }

    /**
     * 判空工具
     *
     * @param o
     */
    public static void checkEmpty(Object o, String msg) {
        if (CommonUtils.isEmpty(o)) {
            throwException(msg);
        }
    }

    public static void checkBizEmpty(Object o, String msg) {
        if (CommonUtils.isEmpty(o)) {
            throwBizException(msg);
        }
    }

    /**
     * 判空工具
     *
     * @param o
     */
    public static void checkEmpty(Object o, String format, Object... args) {
        if (CommonUtils.isEmpty(o)) {
            throwException(format, args);
        }
    }

    public static void checkBizEmpty(Object o, String format, Object... args) {
        if (CommonUtils.isEmpty(o)) {
            throwBizException(format, args);
        }
    }

    /**
     * 抛业务异常
     */
    public static RuntimeException throwBizException(String errMessage) {
        throw new RuntimeException(errMessage);
    }

    /**
     * 抛业务异常
     *
     * @param format
     * @param args
     */
    public static RuntimeException throwBizException(String format, Object... args) {
        throw throwBizException(new Formatter().format(format, args).toString());
    }


    /**
     * 抛异常
     */
    public static RuntimeException throwException(String errMessage) {
        throw new RuntimeException(errMessage);
    }

    /**
     * 抛异常
     *
     * @param format
     * @param args
     */
    public static RuntimeException throwException(String format, Object... args) {
        throw throwException(new Formatter().format(format, args).toString());
    }
}
