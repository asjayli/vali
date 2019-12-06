package com.kitsrc.watt.util;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Created with IntelliJ IDEA
 *
 * @author lijie
 * @date
 * @time
 * @description
 */
public class ObjectUtil extends ObjectUtils {
    private static final String LIST_TYPE_NAME = "java.util.List";


    /**
     * 判断是否为基本类型 包括基本类型的封装类型
     *
     * @param clazz
     * @return Boolean
     * @see String
     * @see Boolean#TYPE
     * @see Character#TYPE
     * @see Byte#TYPE
     * @see Short#TYPE
     * @see Integer#TYPE
     * @see Long#TYPE
     * @see Float#TYPE
     * @see Double#TYPE
     * @see Void#TYPE
     */
    public static boolean isPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return false;
        } else {
            if (clazz.isPrimitive()) {
                return true;
            } else {
                return String.class.equals(clazz) || String[].class.equals(clazz) ||
                       ClassUtil.isPrimitiveArray(clazz) ||
                       ClassUtil.isPrimitiveWrapper(clazz) ||
                       ClassUtil.isPrimitiveWrapperArray(clazz);
            }
        }
    }

    public static boolean isPrimitive(Object o) {
        if (isEmpty(o)) {
            return false;
        } else {
            return isPrimitive(o.getClass());
        }

    }

    public static boolean isPrimitive(Field field) {
        if (isEmpty(field)) {
            return false;
        } else {
            return isPrimitive(field.getType());
        }
    }

    public static boolean isNotPrimitive(Field field) {
        return !isPrimitive(field);
    }

    /**
     * 判断是否为基本类型 包括基本类型的封装类型
     *
     * @param clazz
     * @return
     */
    public static boolean isNotPrimitive(Class<?> clazz) {
        return !isPrimitive(clazz);
    }


    /**
     * 对象判空
     * <p>
     * <pre>
     *     ***********
     *     Object==null
     *     CharSequence.length==0
     *     Collection.isEmpty()==true
     *     Map.isEmpty()==true
     *     Array.Length()==0
     *     ***********
     *     return true
     * </pre>
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj.getClass()
               .isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof CharSequence) {
            return StringUtil.isBlank((CharSequence) obj);
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * Is empty boolean.
     *
     * @param array the array
     * @return the boolean
     */
    public static boolean isEmpty(Object[] array) {
        return !isNotEmpty(array);
    }

    /**
     * Is not empty boolean.
     *
     * @param array the array
     * @return the boolean
     */
    public static boolean isNotEmpty(Object[] array) {
        return array != null && array.length > 0;
    }

    public static Class<?> getCollectionFieldArgsClass(Field collectionField) throws
                                                                              ClassNotFoundException {
        String className = "";
        ParameterizedType listGenericType = (ParameterizedType) collectionField.getGenericType();
        Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
        for (int i = 0; i < listActualTypeArguments.length; i++) {
            className = listActualTypeArguments[i].getTypeName();
        }
        Class<?> collectionClass = Class.forName(className);
        return collectionClass;
    }

    /**
     * Convert the given array (which may be a primitive array) to an
     * object array (if necessary of primitive wrapper objects).
     * <p>A {@code null} source value will be converted to an
     * empty Object array.
     *
     * @param source the (potentially primitive) array
     * @return the corresponding object array (never {@code null})
     */
    private static Object[] toObjectArray(Object source) {
        if (isEmpty(source)) {
            return null;
        }
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return new Object[0];
        }
        if (!source.getClass()
                   .isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }
        int length = Array.getLength(source);
        if (length == 0) {
            return new Object[0];
        }
        Class<?> wrapperType = Array.get(source, 0)
                                    .getClass();
        Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }


    private static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[]) o1, (byte[]) o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[]) o1, (Object[]) o2);
        }
        return false;
    }

    /**
     * 使用Bean的属性值对Map进行填充 不包含nested 不更改Map中原有数据
     * 具体规则如下所示
     * <code>
     * <br> bean.aa=null
     * <br> bean.bb=""
     * <br> bean.cc="cc"
     * <br> bean.dd="dd"
     * <br>
     * <br> map.aa="11"
     * <br> map.bb="22"
     * <br> map.cc="33"
     * <br> 执行 populate(bean,map);
     * <br> map.aa --> "11"
     * <br> map.bb --> "22"
     * <br> map.cc --> "cc"
     * <br> map.dd --> "dd"
     * </code>
     *
     * @param bean
     * @param properties
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("all")
    private void populate(final Object bean, final Map<String, ? extends Object> properties)
    throws IllegalAccessException, InvocationTargetException {

        // Do nothing unless both arguments have been specified
        if ((bean == null) || (properties == null)) {
            return;
        }

        // Loop through the property name/value pairs to be set
        for (final Map.Entry<String, ? extends Object> entry : properties.entrySet()) {
            // Identify the property name and value(s) to be assigned
            final String name = entry.getKey();
            if (name == null) {
                continue;
            }

            //TODO
        }

    }


    /**
     * 将 指定value 安全转换成相应 class类型
     *
     * @param values
     * @param clazz
     * @return
     */
    private <T> T convert(final Object values, final Class<T> clazz) {
        //TODO ObjectUtil.convert 待实现
        return null;
    }

    /**
     * 将 指定value 安全转换成相应 class类型
     *
     * @param values
     * @param clazz
     * @return
     */
    private <T> T convert(final Object[] values, final Class<T> clazz) {
        //TODO ObjectUtil.convert 待实现
        return null;
    }

    /**
     * 将 指定value 安全转换成相应 class类型
     *
     * @param values
     * @param clazz
     * @return
     */
    private <T> T convert(final List<Object> values, final Class<T> clazz) {
        //TODO ObjectUtil.convert 待实现
        return null;
    }

}