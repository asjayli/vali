package com.kitsrc.watt.utils;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created with IntelliJ IDEA </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/07/01  </p>
 * @time : 16:57  </p>
 * Description:  </p>
 */
public class NumberUtil extends NumberUtils {

    /**
     * 整型 转 罗马数字
     *
     * @param num
     * @return
     */
    public static String intToRoman(int num) {
        StringBuilder str = new StringBuilder();
        int temp = 100;
        for (int i = 0; i < 3; i++) {
            while (num >= 10 * temp) {
                str.append("M");
                num -= 10 * temp;
            }
            if (num >= 9 * temp) {
                str.append("CM");
                num -= 9 * temp;
            }
            if (num >= 5 * temp) {
                str.append("D");
                num -= 5 * temp;
            }
            if (num >= 4 * temp && num < 5 * temp) {
                str.append("CD");
                num -= 4 * temp;
            }
            while (num >= temp) {
                str.append("C");
                // 1 * temp
                num -= temp;
            }
            temp = temp / 10;
        }
        return str.toString();
    }

    /**
     * 罗马数字 转 整型
     *
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        // 这个函数是将单个罗马字符转换为数字

        int sum = charToInt(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            // 如果出现 IV,XC,XL这种情况，那么我们需要减去两倍的 I,X等，
            // 因为它前面被我们多加了一次
            if (charToInt(s.charAt(i)) > charToInt(s.charAt(i - 1))) {

                sum = sum + charToInt(s.charAt(i)) - 2 * charToInt(s.charAt(i - 1));
            } else {
                sum = sum + charToInt(s.charAt(i));
            }
        }
        return sum;

    }


    /**
     * 这个函数是将单个罗马字符转换为数字
     *
     * @param c
     * @return
     */
    private static int charToInt(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
}
