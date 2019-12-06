package com.kitsrc.watt.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vandream.hamster.core.util.JSONUtil;
import com.vandream.hamster.core.util.ObjectUtil;
import com.vandream.hamster.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/14
 * @time : 22:30
 * Description:
 */
@Slf4j
public class ReflectUtil {

    /**
     * setToken()方法名称
     */
    private static final String METHOD_SET_TOKEN = "setToken";

    /**
     * 反射调用前参数处理方法<br>
     * 如果请求参数为 [] 格式
     *
     * @param method
     * @param dataArgs
     * @return
     * @throws Exception
     */
    public static Object[] processMethodArgs(Method method, Object dataArgs) {
        if (ObjectUtil.isEmpty(dataArgs)) {
            return null;
        }
        if (dataArgs instanceof JSON) {
            if (dataArgs instanceof JSONObject) {
                return processMethodArgs(method, (JSONObject) dataArgs);
            }
            else if (dataArgs instanceof JSONArray) {
                return processMethodArgs(method, (JSONArray) dataArgs);
            }
        }
        else {
            Object[] methodArgs = {};
            if (ObjectUtil.isEmpty(dataArgs)) {
                return methodArgs;
            }
            final int parameterCount = method.getParameterCount();
            final Class<?>[] methodParameterTypesArray = method.getParameterTypes();
            if (parameterCount == 0) {
                //无参method
                return methodArgs;
            }
            else if (parameterCount == 1) {
                final Class<?> parameterTypeClass = methodParameterTypesArray[0];
                Object parseObject = JSONUtil.parseObject(dataArgs.toString(), parameterTypeClass);
                methodArgs = new Object[] {parseObject};
                return methodArgs;
            }
        }
        return new Object[] {dataArgs};
    }

    /**
     * 反射调用前参数处理方法<br>
     * 如果请求参数为 [] 格式
     *
     * @param method
     * @param dataArgs
     * @return
     * @throws Exception
     */
    public static Object[] processMethodArgs(Method method, JSON dataArgs) {
        if (ObjectUtil.isEmpty(dataArgs)) {
            return null;
        }
        if (dataArgs instanceof JSONObject) {
            return processMethodArgs(method, (JSONObject) dataArgs);
        }
        else /*if (dataArgs instanceof JSONArray)*/ {
            return processMethodArgs(method, (JSONArray) dataArgs);
        }
    }

    /**
     * 反射调用前参数处理方法<br>
     * 如果请求参数为 [] 格式 <br>
     * 例如参数 类型 为 <code>List<***Req> xxList</code>
     *
     * @param method
     * @param dataArgs
     * @return
     * @throws Exception
     */
    public static Object[] processMethodArgs(Method method, JSONArray dataArgs) {
        Object[] methodArgs = {};
        if (ObjectUtil.isEmpty(dataArgs)) {
            return methodArgs;
        }
        final int parameterCount = method.getParameterCount();
        final Class<?>[] methodParameterTypesArray = method.getParameterTypes();
        if (parameterCount == 0) {
            //无参method
            return methodArgs;
        }
        else if (parameterCount == 1) {

            final Class<?> parameterTypeClass = methodParameterTypesArray[0];
            // 基本类型
            if (ObjectUtil.isPrimitive(parameterTypeClass)) {
                final Object object = dataArgs.toJavaObject(parameterTypeClass);
                methodArgs = new Object[] {object};
                return methodArgs;
            }
            // 数组
            final boolean isArray = parameterTypeClass.isArray();
            if (isArray) {
                // method 参数类型为 数组

                final Object[] objectArray = dataArgs.toArray();
                methodArgs = new Object[] {objectArray};
                return methodArgs;
            }
            final Object object = dataArgs.toJavaObject(parameterTypeClass);
            methodArgs = new Object[] {object};
            return methodArgs;
        }
        else {
            return methodArgs;
        }
    }


    /**
     * 反射调用前参数处理方法<br>
     * 如果请求参数为 {} 格式
     *
     * @param method
     * @param dataArgs
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static Object[] processMethodArgs(Method method, JSONObject dataArgs) {
        Object[] methodArgs = {};
        if (ObjectUtil.isEmpty(dataArgs)) {
            return methodArgs;
        }
        int parameterCount = method.getParameterCount();
        Parameter[] parametersArray = method.getParameters();

        if (0 == parameterCount) {
            return methodArgs;
        }
        else if (1 == parameterCount) {
            methodArgs = new Object[1];
            //只有一个参数 仅填充 args[0]
            Parameter parameter = parametersArray[0];
            final String parameterName = parameter.getName();
            Class<?> parameterTypeClass = parameter.getType();
            if (ObjectUtil.isPrimitive(parameterTypeClass)) {
                final Object parameterValue = dataArgs.getObject(parameterName, parameterTypeClass);
                methodArgs[0] = parameterValue;
                return methodArgs;
            }
            else {
                final Object object = dataArgs.toJavaObject(parameterTypeClass);
                methodArgs = new Object[] {object};
                return methodArgs;
            }
        }
        else {
            methodArgs = new Object[parameterCount];

            for (int i = 0; i < parametersArray.length; i++) {
                Parameter parameter = parametersArray[i];
                Class<?> parameterTypeClass = parameter.getType();
                String argName = parameter.getName();
                Object data = dataArgs.getObject(argName, parameterTypeClass);
                if (ObjectUtil.isEmpty(data)) {
                    //参数值 为空 返回空参数实例
                    methodArgs[i] = null;
                }
                else {
                    methodArgs[i] = data;
                }
            }
        }
        return methodArgs;
    }

    /**
     * 反射调用工具类
     *
     * @param method
     * @param serviceBean
     * @param args
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invoke(Method method, Object serviceBean, Object[] args) throws InvocationTargetException,
            IllegalAccessException {


        if (ObjectUtil.isNotEmpty(method) && ObjectUtil.isNotEmpty(serviceBean)) {
            log.debug("invoke 开始调用 name:{} args:{} {} class:{}", method.getName(), JSONUtil.toJSONString(args),
                    System.lineSeparator(),
                    serviceBean.getClass()
                            .getName());
        }
        Object data = null;
        // 设置method 对象可达
        method.setAccessible(true);


        if (ObjectUtil.isEmpty(args) || method.isVarArgs()) {
            // 无参调用
            data = method.invoke(serviceBean);
        }
        else {
            data = method.invoke(serviceBean, args);
        }
        return data;
    }

    /**
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。
     *
     * @param clazz      目标类
     * @param methodName 方法名
     * @param classes    方法参数类型数组
     * @return 方法对象
     * @throws Exception
     */
    public static Method getMethod(Class clazz, String methodName,
            final Class[] classes) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        }
        catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            }
            catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                }
                else {
                    method = getMethod(clazz.getSuperclass(), methodName,
                            classes);
                }
            }
        }
        return method;
    }

    /**
     * @param obj        调整方法的对象
     * @param methodName 方法名
     * @param classes    参数类型数组
     * @param objects    参数数组
     * @return 方法的返回值
     * @throws RuntimeException
     */
    public static Object invoke(final Object obj, final String methodName,
            final Class[] classes, final Object[] objects) throws RuntimeException {
        try {
            Method method = getMethod(obj.getClass(), methodName, classes);
            // 设置对象可达
            method.setAccessible(true);
            return method.invoke(obj, objects);
        }
        catch (NoSuchMethodException nsme) {
            throw new RuntimeException("Service method does not exist",
                    nsme);
        }
        catch (SecurityException se) {
            throw new RuntimeException("Access denied", se);
        }
        catch (IllegalAccessException iae) {
            throw new RuntimeException("Method not accessible", iae);
        }
        catch (IllegalArgumentException iarge) {
            throw new RuntimeException("Invalid parameter match", iarge);
        }
        catch (InvocationTargetException ite) {
            throw new RuntimeException(
                    "Service target threw an unexpected exception", ite);
        }
        catch (NullPointerException npe) {
            throw new RuntimeException("Specified object is null", npe);
        }
        catch (ExceptionInInitializerError eie) {
            throw new RuntimeException("Initialization failed", eie);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public static Object invoke(final Object obj, final String methodName,
            final Class[] classes) throws RuntimeException {
        return invoke(obj, methodName, classes, new Object[] {});
    }

    public static Object invoke(final Object obj, final String methodName) throws RuntimeException {
        return invoke(obj, methodName, new Class[] {}, new Object[] {});
    }

    /**
     * @param className  调用方法的对象
     * @param methodName 方法名
     * @param classes    方法参数类型数组
     * @param objects    方法参数值数组
     * @return
     * @throws RuntimeException
     */
    public static Object invoke(final String className, final String methodName,
            final Class[] classes, final Object[] objects) throws RuntimeException {
        if (StringUtil.isEmpty(className)) {
            throw new RuntimeException("class name could not be null");
        }
        if (StringUtil.isEmpty(methodName)) {
            throw new RuntimeException("method name could not be null");
        }
        try {
            Class<?> clz = Class.forName(className);
            Object obj = clz.newInstance();
            Method method = getMethod(obj.getClass(), methodName, classes);
            return method.invoke(obj, objects);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Service Object does not exists", e);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Method not accessible", e);
        }
        catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("Service Object Serialize Fail");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Object invoke(final String className, final String methodName,
            final Class[] classes) throws RuntimeException {
        return invoke(className, methodName, classes, new Object[] {});
    }

    public static Object invoke(final String className, final String methodName) throws RuntimeException {
        return invoke(className, methodName, new Class[] {}, new Object[] {});
    }
}