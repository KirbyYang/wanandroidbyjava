package com.wanandroid.java.base.util;

/**
 * Created by godliness on 2019/1/8.
 * From the BaoBao project
 *
 * @author godliness
 * @see java.util.Objects
 * 辅助{@link java.util.Objects}
 */

public class ObjectUtil {

    /**
     * 比较两个对象是否相等
     */
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    /**
     * Object转换成String
     */
    public static String toString(Object o) {
        return String.valueOf(o);
    }

    /**
     * Object转换成String defualt value
     */
    public static String toString(Object o, String nullDefault) {
        return (o != null) ? o.toString() : nullDefault;
    }

    public static String getSimpleName(Object o) {
        return o != null ? o.getClass().getSimpleName() : "?-?";
    }

    /**
     * Object hashCode
     */
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }
}
