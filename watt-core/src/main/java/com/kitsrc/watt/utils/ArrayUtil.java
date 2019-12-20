package com.kitsrc.watt.utils;

import java.lang.reflect.Array;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created with IntelliJ IDEA </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/09/23  </p>
 * @time : 19:20  </p>
 * Description:  </p>
 */
public class ArrayUtil extends ArrayUtils {

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

