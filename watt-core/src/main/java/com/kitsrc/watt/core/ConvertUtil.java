package com.kitsrc.watt.core;


import com.kitsrc.watt.constant.ClassConstants;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/3/21
 * @time : 19:35
 * Description:
 */
public class ConvertUtil {
    /**
     * 将Object 转为BigDecimal 类型数据
     *
     * @param obj Object
     * @return返回BigDecimal 类型数据
     */
    public static BigDecimal toBigDecimal(Object obj) {
        if (obj != null) {
            if (ClassConstants.STRING_DEAL.equals(obj.getClass()
                                                     .getName()) && !StringUtil.isEmpty((String) obj)) {
                return toBigDecimal((String) obj);
            } else if (ClassConstants.INTEGER_DEAL.equals(obj.getClass()
                                                             .getName())) {
                return toBigDecimal((Integer) obj);
            } else if (ClassConstants.BIGDECIMAL_DEAL.equals(obj
                                                                     .getClass()
                                                                     .getName())) {
                return toBigDecimal((BigDecimal) obj);
            } else if (ClassConstants.LONG_DEAL.equals(obj.getClass()
                                                          .getName())) {
                return toBigDecimal((Long) obj);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
