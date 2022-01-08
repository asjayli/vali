package com.vali.util.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterNamesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

/**
 * Reflection tools
 *
 * @author zhangsen
 */
public class ReflectionUtil {

    /**
     * The constant MAX_NEST_DEPTH.
     */
    public static final int MAX_NEST_DEPTH = 20;

    /**
     * 组装反射扫描器
     * 当前已注册扫描器：<br>
     *
     * @param packageName 包路径
     * @return
     * @see org.reflections.scanners.TypeAnnotationsScanner
     * @see org.reflections.scanners.SubTypesScanner
     * @see org.reflections.scanners.MethodAnnotationsScanner
     * @see org.reflections.scanners.MethodParameterNamesScanner
     * @see org.reflections.scanners.FieldAnnotationsScanner
     */
    private static Reflections getReflections(String packageName) {
        Reflections reflections = new Reflections(packageName,
                new MethodAnnotationsScanner(),
                new TypeAnnotationsScanner(),
                new SubTypesScanner(),
                new MethodParameterNamesScanner(),
                new FieldAnnotationsScanner());
        return reflections;
    }

    /**
     * 获取该路径下 所有包含 该注解 的 类 的 class Set集合
     *
     * @param packageName 包路径
     * @param classAnnotation  类上的注解
     * @return
     */
    public static Set<Class<?>> getTypesAnnotatedSet(@Nonnull final String packageName,
            @Nonnull final Class<? extends Annotation> classAnnotation) {
        Reflections reflections = getReflections(packageName);
        Set<Class<?>> classesSet = reflections.getTypesAnnotatedWith(classAnnotation);
        return classesSet;
    }

    /**
     * 获取该路径下 所有包含 该注解 的 方法 的 Method 对象集合 Set集合
     *
     *
     * @param packageName 包路径
     * @param methodAnnotation
     * @return
     */
    public static Set<Method> getMethodsAnnotatedSet(@Nonnull final String packageName,
            @Nonnull final Class<? extends Annotation> methodAnnotation) {
        Reflections reflections = getReflections(packageName);
        Set<Method> methodSet = reflections.getMethodsAnnotatedWith(methodAnnotation);
        return methodSet;
    }

    /**
     * Gets class by name.
     *
     * @param className the class name
     * @return the class by name
     * @throws ClassNotFoundException the class not found exception
     */
    public static Class<?> getClassByName(String className) throws ClassNotFoundException {
        return Class.forName(className, true, Thread.currentThread()
                .getContextClassLoader());
    }

    /**
     * get Field Value
     *
     * @param target    the target
     * @param fieldName the field name
     * @return field value
     * @throws NoSuchFieldException     the no such field exception
     * @throws SecurityException        the security exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException   the illegal access exception
     */
    public static Object getFieldValue(Object target, String fieldName)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Class<?> cl = target.getClass();
        int i = 0;
        while ((i++) < MAX_NEST_DEPTH && cl != null) {
            try {
                Field field = cl.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(target);
            }
            catch (Exception e) {
                cl = cl.getSuperclass();
            }
        }
        throw new NoSuchFieldException("class:" + target.getClass() + ", field:" + fieldName);
    }

    /**
     * invoke Method
     *
     * @param target     the target
     * @param methodName the method name
     * @return object
     * @throws NoSuchMethodException     the no such method exception
     * @throws SecurityException         the security exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws IllegalArgumentException  the illegal argument exception
     * @throws InvocationTargetException the invocation target exception
     */
    public static Object invokeMethod(Object target, String methodName)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Class<?> cl = target.getClass();
        int i = 0;
        while ((i++) < MAX_NEST_DEPTH && cl != null) {
            try {
                Method m = cl.getDeclaredMethod(methodName);
                m.setAccessible(true);
                return m.invoke(target);
            }
            catch (Exception e) {
                cl = cl.getSuperclass();
            }
        }
        throw new NoSuchMethodException("class:" + target.getClass() + ", methodName:" + methodName);
    }

    /**
     * invoke Method
     *
     * @param target         the target
     * @param methodName     the method name
     * @param parameterTypes the parameter types
     * @param args           the args
     * @return object
     * @throws NoSuchMethodException     the no such method exception
     * @throws SecurityException         the security exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws IllegalArgumentException  the illegal argument exception
     * @throws InvocationTargetException the invocation target exception
     */
    public static Object invokeMethod(Object target, String methodName, Class<?>[] parameterTypes, Object[] args)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Class<?> cl = target.getClass();
        int i = 0;
        while ((i++) < MAX_NEST_DEPTH && cl != null) {
            try {
                Method m = cl.getDeclaredMethod(methodName, parameterTypes);
                m.setAccessible(true);
                return m.invoke(target, args);
            }
            catch (Exception e) {
                cl = cl.getSuperclass();
            }
        }
        throw new NoSuchMethodException("class:" + target.getClass() + ", methodName:" + methodName);
    }

    /**
     * invoke static Method
     *
     * @param targetClass     the target class
     * @param methodName      the method name
     * @param parameterTypes  the parameter types
     * @param parameterValues the parameter values
     * @return object
     * @throws NoSuchMethodException     the no such method exception
     * @throws SecurityException         the security exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws IllegalArgumentException  the illegal argument exception
     * @throws InvocationTargetException the invocation target exception
     */
    public static Object invokeStaticMethod(Class<?> targetClass, String methodName, Class<?>[] parameterTypes,
            Object[] parameterValues)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        int i = 0;
        while ((i++) < MAX_NEST_DEPTH && targetClass != null) {
            try {
                Method m = targetClass.getMethod(methodName, parameterTypes);
                return m.invoke(null, parameterValues);
            }
            catch (Exception e) {
                targetClass = targetClass.getSuperclass();
            }
        }
        throw new NoSuchMethodException("class:" + targetClass + ", methodName:" + methodName);
    }

    /**
     * get Method by name
     *
     * @param classType      the class type
     * @param methodName     the method name
     * @param parameterTypes the parameter types
     * @return method
     * @throws NoSuchMethodException the no such method exception
     * @throws SecurityException     the security exception
     */
    public static Method getMethod(Class<?> classType, String methodName, Class<?>[] parameterTypes)
            throws NoSuchMethodException, SecurityException {
        return classType.getMethod(methodName, parameterTypes);
    }

    /**
     * get all interface of the clazz
     *
     * @param clazz the clazz
     * @return set
     */
    public static Set<Class<?>> getInterfaces(Class<?> clazz) {
        if (clazz.isInterface()) {
            return Collections.singleton(clazz);
        }
        Set<Class<?>> interfaces = new LinkedHashSet<Class<?>>();
        while (clazz != null) {
            Class<?>[] ifcs = clazz.getInterfaces();
            for (Class<?> ifc : ifcs) {
                interfaces.addAll(getInterfaces(ifc));
            }
            clazz = clazz.getSuperclass();
        }
        return interfaces;
    }

}
