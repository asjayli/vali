package com.kitsrc.watt.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vandream.hamster.core.util.annotations.FieldAlias;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import org.springframework.beans.BeanUtils;

/**
 * POJO 类处理工具
 *
 * @author : LiJie
 * @date : 2019/1/18
 * @time : 16:35
 * Description:
 */
@Slf4j
public class BeanUtil extends BeanUtils {
    private static final String LIST_TYPE_NAME = "java.util.List";

    //public static void copyProperties(final Object source, final Object target) {
    //    String beanKey = generateKey(source.getClass(), target.getClass());
    //    BeanCopier copier = null;
    //    if (!beanCopierMap.containsKey(beanKey)) {
    //        copier = BeanCopier.create(source.getClass(), target.getClass(), false);
    //        beanCopierMap.put(beanKey, copier);
    //    } else {
    //        copier = beanCopierMap.get(beanKey);
    //    }
    //    copier.copy(source, target, null);
    //
    //}

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    /**
     * 赋值对象属性 兼容{@code @FieldAlias}
     *
     * @param target
     * @param source
     * @param isExcludeStatic 是否排除静态字段
     * @param isExcludeFinal  是否排除final字段
     * @throws Exception
     */
    public static void copyProperties(final Object target, final Object source, boolean isExcludeStatic, boolean isExcludeFinal) {
        try {
            Class<?> sourceClass = source.getClass();
            Class<?> targetClass = target.getClass();
            List<Field> targetFields = new ArrayList<>();
            Field[] thisFields = targetClass.getDeclaredFields();
            targetFields.addAll(Arrays.asList(thisFields));

            Class<?> targetSuperclass = targetClass.getSuperclass();
            while (targetSuperclass != null) {
                // 获取父类属性
                Field[] superFields = targetSuperclass.getDeclaredFields();
                targetFields.addAll(Arrays.asList(superFields));
                targetSuperclass = targetSuperclass.getSuperclass();
            }
            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                //是否排除静态字段
                if (isExcludeStatic) {
                    if (Modifier.isStatic(targetField.getModifiers())) {
                        continue;
                    }
                }
                //是否排除final字段
                if (isExcludeFinal) {
                    if (Modifier.isFinal(targetField.getModifiers())) {
                        continue;
                    }
                }
                String fieldName = null;
                //判断字段别名
                FieldAlias fieldAlias = targetField.getAnnotation(FieldAlias.class);
                if (fieldAlias == null) {
                    fieldName = targetField.getName();
                }
                else {
                    fieldName = fieldAlias.value();
                }
                //获取对应字段Field
                Field sourceFields = null;
                try {
                    sourceFields = sourceClass.getDeclaredField(fieldName);
                    sourceFields.setAccessible(true);
                }
                catch (Exception e) {
                    try {
                        sourceFields = sourceClass.getSuperclass()
                                .getDeclaredField(fieldName);
                        sourceFields.setAccessible(true);
                    }
                    catch (Exception e1) {
                        continue;
                    }
                }
                //赋值到目标字段
                Object sourceValue = sourceFields.get(source);
                if (ObjectUtil.isNotEmpty(sourceValue)) {
                    //校验双方类型是否匹配
                    String targetTypeName = targetField.getType()
                            .getTypeName();
                    String sourceTypeName = sourceFields.getType()
                            .getTypeName();
                    if (targetTypeName.equals(sourceTypeName)) {
                        if (LIST_TYPE_NAME.equals(sourceTypeName)) {
                            Class<?> collectionFieldArgsClass = ObjectUtil.getCollectionFieldArgsClass(targetField);
                            if (ObjectUtil.isNotPrimitive(collectionFieldArgsClass)) {
                                List<?> transfer = convert((List) sourceValue, collectionFieldArgsClass);
                                targetField.set(target, transfer);
                            }
                            else {
                                targetField.set(target, sourceValue);
                            }
                        }
                        else {
                            targetField.set(target, sourceValue);
                        }
                        targetField.set(target, sourceValue);
                    }
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * POJO 对象转化 {@code List} 兼容{@code @FieldAlias}
     *
     * @param list  源对象List
     * @param clazz 目标对象class
     * @param <T>
     * @return
     * @see BeanUtil#convert
     */
    @Deprecated
    public static <T> List<T> transfer(List<?> list, Class<T> clazz) {
        return convert(list, clazz);
    }

    /**
     * POJO 对象转化 兼容{@code @FieldAlias}
     *
     * @param object 源对象object
     * @param clazz  目标对象class
     * @param <T>
     * @return
     * @see BeanUtil#convert
     */
    @Deprecated
    public static <T> T transfer(Object object, Class<T> clazz) {
        return convert(object, clazz);
    }

    /**
     * POJO 对象转化 兼容{@code @FieldAlias}
     *
     * @param object 源对象object
     * @param clazz  目标对象class
     * @param <T>
     * @return
     */
    public static <T> T convert(Object object, Class<T> clazz) {
        try {
            if (ObjectUtil.isEmpty(object)) {
                return null;
            }
            T instance = clazz.newInstance();
            copyProperties(instance, object, true, true);
            return instance;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * POJO 对象转化 {@code List} 兼容{@code @FieldAlias}
     *
     * @param list  源对象List
     * @param clazz 目标对象class
     * @param <T>
     * @return
     */
    public static <T> List<T> convert(List<?> list, Class<T> clazz) {
        List<T> resultList = new ArrayList<>();
        if (ObjectUtil.isEmpty(list)) {
            return resultList;
        }
        for (Object object : list) {
            T instance = convert(object, clazz);
            resultList.add(instance);
        }
        return resultList;
    }

    private static final String CREATE = "create";
    private static final String MODIFY = "modify";
    private static final List<String> FIELD_NAME_WITH_LIST = Arrays.asList("id", "name", "code", "date", "time");

    /**
     * 将创建人Id，创建人Name，创建时间设置为null
     *
     * @param obj
     * @return
     */
    public static void setCreateFieldBeNull(final Object obj) {

        try {
            Field[] declaredFields = obj.getClass()
                    .getDeclaredFields();
            Field[] superDeclaredFields = obj.getClass()
                    .getSuperclass()
                    .getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String fieldName = declaredField.getName();
                if (isCreateField(fieldName)) {
                    declaredField.setAccessible(true);
                    declaredField.set(obj, null);
                }
            }
            for (Field declaredField : superDeclaredFields) {
                String name = declaredField.getName();
                if (StringUtil.isNotBlank(name) && name.startsWith("create")) {
                    declaredField.setAccessible(true);
                    declaredField.set(obj, null);
                }
            }

        }
        catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static boolean isCreateField(String fieldName) {
        return fieldNameWith(fieldName, CREATE);
    }

    private static boolean fieldNameWith(String fieldName, String withStr) {
        if (StringUtil.isBlank(fieldName)) {
            return false;
        }
        else {
            String lowerCaseName = fieldName.toLowerCase();

            if (lowerCaseName.startsWith(withStr)) {
                for (String with : FIELD_NAME_WITH_LIST) {
                    if (lowerCaseName.endsWith(with)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public static boolean isModifyField(String fieldName) {
        return fieldNameWith(fieldName, MODIFY);
    }

    /**
     * 将更新人 id,name,code,date,time，更新时间设置为null
     *
     * @param obj
     * @return
     */
    public static void setModifyFieldBeNull(final Object obj) {

        try {
            Field[] declaredFields = obj.getClass()
                    .getDeclaredFields();
            Field[] superDeclaredFields = obj.getClass()
                    .getSuperclass()
                    .getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String name = declaredField.getName();
                if (StringUtil.isNotBlank(name) && name.startsWith("modify")) {
                    declaredField.setAccessible(true);
                    declaredField.set(obj, null);
                }
            }
            for (Field declaredField : superDeclaredFields) {
                String fieldName = declaredField.getName();
                if (isModifyField(fieldName)) {
                    declaredField.setAccessible(true);
                    declaredField.set(obj, null);
                }
            }
        }
        catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    //private static BeanCopier _createCopier(Class<?> clz) {
    //    if (beanCopiers.containsKey(clz)) {
    //        return beanCopiers.get(clz);
    //    }
    //    beanCopiers.putIfAbsent(clz, BeanCopier.create(clz, clz, true));
    //    return beanCopiers.get(clz);
    //
    //}

    //public <T> T cloneable(Class<T> clazz) {
    //    try {
    //        T clone = clazz
    //                .newInstance();
    //
    //        BeanCopier copier = _createCopier(this.getClass());
    //
    //        copier.copy(this, clone, new Converter() {
    //            @Override
    //            public Object convert(Object bean, Class fieldType, Object fieldName) {
    //                if (bean == null) {
    //                    return null;
    //                } else if (bean instanceof ICloneable) {
    //                    return ((ICloneable) bean).clone();
    //                } else {
    //
    //                    if (bean.getClass()
    //                            .isArray() && !bean.getClass()
    //                                               .getComponentType()
    //                                               .equals(byte.class)) {
    //                        int length = Array.getLength(bean);
    //                        Object clone = Array.newInstance(bean.getClass()
    //                                                             .getComponentType(), length);
    //                        for (int i = 0; i < length; i++) {
    //                            Array.set(clone, i, _clone(Array.get(bean, i)));
    //                        }
    //                        return clone;
    //                    } else {
    //                        return bean;
    //                    }
    //                }
    //            }
    //        });
    //
    //        return clone;
    //    } catch (Exception e) {
    //        log.error(e.getMessage(), e);
    //        throw new RuntimeException(e);
    //    }
    //}

    /**
     * 将Map的值复制到java对象的同名属性
     *
     * @param targetBean
     * @param dataMap
     * @param ignoreEmptyString
     */
    public static void copyMap2Pojo(Object targetBean, Map dataMap,
            boolean ignoreEmptyString) {
        try {
            PropertyDescriptor[] origDescriptors = PropertyUtils
                    .getPropertyDescriptors(targetBean);

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue;
                }
                //zhf 2012-1-20
//				if("objectId".equals(name))
//					continue;

                if (PropertyUtils.isWriteable(targetBean, name)) {
                    Object obj = dataMap.get(name);
                    if (obj == null) {
                        continue;
                    }

                    if ((obj.toString()
                            .trim()).length() == 0
                            && ignoreEmptyString) {
                        continue;
                    }
                    obj = convertValue(origDescriptors[i], obj);
                    org.apache.commons.beanutils.BeanUtils.copyProperty(targetBean, name, obj);
                }
            }// for end
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 将一个对象的属性值取出来放置到Map中。Map的Key为对象属性名称
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> toMap(Object bean) {
        if (bean == null) {
            return null;
        }

        Map<String, Object> dataMap = new HashMap<>();
        try {
            PropertyDescriptor[] origDescriptors = PropertyUtils
                    .getPropertyDescriptors(bean);

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue;
                }
                //zhf 2012-1-20
//				if("objectId".equals(name))
//					continue;

                if (PropertyUtils.isReadable(bean, name)) {
                    Object obj = PropertyUtils.getProperty(bean, name);
                    if (obj == null) {
                        continue;
                    }
                    obj = convertValue(origDescriptors[i], obj);
                    dataMap.put(name, obj);
                }
            }// for end
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return dataMap;
    }

    private static Object convertValue(PropertyDescriptor origDescriptor,
            Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj.toString()
                .trim()
                .length() == 0) {
            //add by WY String 类型，返回空字符串
            if (origDescriptor.getPropertyType() == String.class) {
                return "";
            }
            else {
                return null;
            }
        }
        if (origDescriptor.getPropertyType() == Date.class) {
            // 同一个时间，第一次从界面层传过来时，obj为String类型;转化后为Date类型
            if (obj instanceof Date) {
                return obj;
            }
            else {
                try {
                    // 修改 时间转换时会把带时分秒的截掉的问题 2012-5-10 张慧峰
                    if (obj.toString()
                            .length() > 10) {
                        obj = DateUtil.toDateTime(obj.toString());
                    }
                    else {
                        obj = DateUtil.toDate(obj.toString());
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
}
