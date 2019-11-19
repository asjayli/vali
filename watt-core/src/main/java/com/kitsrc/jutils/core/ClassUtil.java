package com.kitsrc.jutils.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;

/**
 * Created with IntelliJ IDEA </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/09/29  </p>
 * @time : 15:57  </p>
 * Description:  </p>
 */
public class ClassUtil extends ClassUtils {

    /**
     * Maps names of primitives to their corresponding primitive {@code Class}es.
     */
    private static final Map<String, Class<?>> NAME_PRIMITIVE_MAP = new HashMap<>();

    static {
        ClassUtil.NAME_PRIMITIVE_MAP.put("boolean", Boolean.class);
        ClassUtil.NAME_PRIMITIVE_MAP.put("byte", Byte.class);
        ClassUtil.NAME_PRIMITIVE_MAP.put("char", Character.class);
        ClassUtil.NAME_PRIMITIVE_MAP.put("short", Short.class);
        ClassUtil.NAME_PRIMITIVE_MAP.put("int", Integer.class);
        ClassUtil.NAME_PRIMITIVE_MAP.put("long", Long.class);
        ClassUtil.NAME_PRIMITIVE_MAP.put("double", Double.class);
        ClassUtil.NAME_PRIMITIVE_MAP.put("float", Float.class);
        ClassUtil.NAME_PRIMITIVE_MAP.put("void", Void.class);
    }

    /**
     * 获取基本类型 封装类型 class
     * @param primitiveClassName
     * @return
     */
    public static Class<?> getPrimitiveBoxClass(String primitiveClassName) {
        return ClassUtil.NAME_PRIMITIVE_MAP.get(primitiveClassName);
    }

    /**
     * Check if the given class represents an array of primitives,
     * i.e. boolean, byte, char, short, int, long, float, or double.
     *
     * @param clazz the class to check
     * @return whether the given class is a primitive array class
     */
    public static boolean isPrimitiveArray(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return (clazz.isArray() && clazz.getComponentType()
                .isPrimitive());
    }

    /**
     * Check if the given class represents an array of primitive wrappers,
     * i.e. Boolean, Byte, Character, Short, Integer, Long, Float, or Double.
     *
     * @param clazz the class to check
     * @return whether the given class is a primitive wrapper array class
     */
    public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return (clazz.isArray() && ClassUtils.isPrimitiveWrapper(clazz.getComponentType()));
    }
}
