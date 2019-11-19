package com.kitsrc.jutils.core.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.kitsrc.jutils.core.ClassUtil;
import com.kitsrc.jutils.core.ObjectUtil;
import net.sf.cglib.core.Converter;

/**
 * Created with IntelliJ IDEA </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/09/29  </p>
 * @time : 14:35  </p>
 * Description:  </p>
 */
public class BeanCopierConverter implements Converter {


    /**
     * BeanCopier 属性类型转换
     * @param sourceFieldValue 源对象属性
     * @param targetFieldClass 目标对象属性
     * @param context 目标对象setter方法名
     * @return
     */
    @Override
    public Object convert(Object sourceFieldValue, Class targetFieldClass, Object context) {
        if (ObjectUtil.isEmpty(sourceFieldValue)) {
            return sourceFieldValue;
        }
        Class<?> sourceFieldClass = sourceFieldValue.getClass();
        String targetFieldClassName = targetFieldClass.getName();
        // 基本类型处理
        Class<?> targetPrimitiveBoxClass = ClassUtil.getPrimitiveBoxClass(targetFieldClassName);
        if (targetPrimitiveBoxClass != null) {
            targetFieldClass = targetPrimitiveBoxClass;
        }
        // S::T 属性类型匹配
        if (sourceFieldClass.equals(targetFieldClass)) {
            return sourceFieldValue;
        }
        // targetFiled 类型为 String 直接转换
        if (String.class.equals(targetFieldClass)) {
            if (sourceFieldValue instanceof BigDecimal) {
                BigDecimal decimal = (BigDecimal) sourceFieldValue;
                return decimal.toPlainString();
            }
            else {
                return sourceFieldValue.toString();
            }

        }
        else if (sourceFieldValue instanceof Timestamp) {
            if (java.sql.Date.class.equals(targetFieldClass)) {
                final Timestamp timestamp = (Timestamp) sourceFieldValue;
                final long timestampTime = timestamp.getTime();
                return new java.sql.Date(timestampTime);
            }
        }
        else if (sourceFieldValue instanceof java.sql.Date) {
            if (java.util.Date.class.equals(targetFieldClass)) {
                final java.sql.Date sqlDate = (java.sql.Date) sourceFieldValue;
                final long sqlDateTime = sqlDate.getTime();
                return new java.util.Date(sqlDateTime);
            }
            else if (Timestamp.class.equals(targetFieldClass)) {
                final java.sql.Date sqlDate = (java.sql.Date) sourceFieldValue;
                final long sqlDateTime = sqlDate.getTime();
                return new Timestamp(sqlDateTime);
            }
        }
        else if (sourceFieldValue instanceof java.util.Date) {
            if (java.sql.Date.class.equals(targetFieldClass)) {
                final java.util.Date utilDate = (java.util.Date) sourceFieldValue;
                final long utilDateTime = utilDate.getTime();
                return new java.sql.Date(utilDateTime);
            }
            else if (Timestamp.class.equals(targetFieldClass)) {
                final java.util.Date utilDate = (java.util.Date) sourceFieldValue;
                final long utilDateTime = utilDate.getTime();
                return new Timestamp(utilDateTime);
            }
        }

        return sourceFieldValue;
    }
}