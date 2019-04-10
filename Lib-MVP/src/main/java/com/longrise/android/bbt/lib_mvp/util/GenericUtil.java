package com.longrise.android.bbt.lib_mvp.util;

import java.lang.reflect.ParameterizedType;

/**
 * Created by godliness on 2017/2/13.
 *
 * @author godliness
 *         类上面的泛型转化成对象，只能调用无参构造方法
 */

public class GenericUtil {

    public static <T> T getT(Object o, int i) {
        try {
            if (o.getClass().getGenericSuperclass() instanceof ParameterizedType) {
                return ((Class<T>) ((ParameterizedType) o.getClass().getGenericSuperclass()).getActualTypeArguments()[i]).newInstance();
            }
        } catch (Exception e) {
            MvpLog.print(e);
        }
        return null;
    }

}
