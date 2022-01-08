package com.vali.util;

import java.lang.reflect.Array;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/3/21
 * @time : 13:25
 * Description:
 */
public class ArrayUtil {
    /**
     * 数组合并 synchronized 保证安全性 并且性能相对 较高
     *
     * @param arrays
     * @param <T>
     * @return
     */
    public static synchronized <T> T[] mergeArrays(T[]... arrays) {
        int lengthOfNewArray = 0;
        for (int i = 0; i < arrays.length; ++i) {
            lengthOfNewArray += arrays[i].length;
        }
        //使用(T[])Array.newInstance(...)可避免(T[])new Object[...]引发的ClassCastException
        T[] newArray = (T[]) Array.newInstance(arrays[0].getClass()
                .getComponentType(), lengthOfNewArray);
        int destPos = 0;
        for (int i = 0; i < arrays.length; ++i) {
            System.arraycopy(arrays[i], 0, newArray, destPos, arrays[i].length);
            destPos += arrays[i].length;
        }
        return newArray;
    }

}
