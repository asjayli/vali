package com.kitsrc.watt.utils.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Created with IntelliJ IDEA </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/09/29  </p>
 * @time : 13:28  </p>
 * Description:  </p>
 */
public class BeanUtil {
    /**
     * 缓存 BeanCopier 实例
     */
    private static final Map<String, BeanCopier> BEAN_COPIERS_CACHE = new HashMap<>();
    private static final Map<String, ConstructorAccess> CONSTRUCTOR_ACCESS_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, MethodAccess> METHOD_ACCESS_CACHE = new ConcurrentHashMap<>();

    /**
     *
     * @param sourceObject
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copyProperties(S sourceObject, Class<T> targetClass) {
        return BeanUtil.copyProperties(sourceObject, targetClass, false);
    }

    /**
     *
     * @param sourceObject
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copyProperties(S sourceObject, Class<T> targetClass, boolean useConverter) {
        Class<?> sourceClass = sourceObject.getClass();
        BeanCopier beanCopier = BeanUtil.getBeanCopier(sourceClass, targetClass, useConverter);
        ConstructorAccess<T> constructorAccess = BeanUtil.getConstructorAccess(targetClass);
        T targetObject = constructorAccess.newInstance();
        if (useConverter) {
            // 如果使用了转换器 则仅根据转换器的规则进行属性复制
            beanCopier.copy(sourceObject, targetObject, new BeanCopierConverter());
        }
        else {
            beanCopier.copy(sourceObject, targetObject, null);
        }
        return targetObject;
    }

    /**
     *
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        ConstructorAccess<T> constructorAccess = BeanUtil.getConstructorAccess(targetClass);
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (Object sourceObject : sourceList) {
            T targetObject = BeanUtil.copyProperties(sourceObject, targetClass);
            targetList.add(targetObject);
        }
        return targetList;
    }

    private static String getConstructorAccessCacheKey(Class targetClass) {
        return targetClass.getName();
    }

    /**
     * 获取 ConstructorAccess 对象
     * @param <T>
     * @param targetClass
     * @return
     */
    private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
        String constructorAccessCacheKey = BeanUtil.getConstructorAccessCacheKey(targetClass);
        if (BeanUtil.CONSTRUCTOR_ACCESS_CACHE.containsKey(constructorAccessCacheKey)) {
            return BeanUtil.CONSTRUCTOR_ACCESS_CACHE.get(constructorAccessCacheKey);
        }
        else {
            try {
                ConstructorAccess constructorAccess = ConstructorAccess.get(targetClass);
                constructorAccess.newInstance();
                BeanUtil.CONSTRUCTOR_ACCESS_CACHE
                        .put(BeanUtil.getConstructorAccessCacheKey(targetClass), constructorAccess);
                return constructorAccess;
            }
            catch (Exception e) {
                throw new RuntimeException(String
                        .format("Create new ConstructorAccess instance of %s failed: %s", targetClass, e.getMessage()));
            }
        }
    }

    private static String getBeanCopierCacheKey(Class<?> class1, Class<?> class2) {
        return class1.getName() + "##" + class2.getName();
    }

    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass) {
        return BeanUtil.getBeanCopier(sourceClass, targetClass, false);
    }

    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass, boolean useConverter) {
        String beanCopierCacheKey = BeanUtil.getBeanCopierCacheKey(sourceClass, targetClass);

        if (BeanUtil.BEAN_COPIERS_CACHE.containsKey(beanCopierCacheKey)) {
            return BeanUtil.BEAN_COPIERS_CACHE.get(beanCopierCacheKey);

        }
        else {
            try {
                BeanCopier beanCopier = BeanCopier.create(sourceClass, targetClass, useConverter);
                BeanUtil.BEAN_COPIERS_CACHE.put(beanCopierCacheKey, beanCopier);
                return beanCopier;
            }
            catch (Exception e) {
                throw new RuntimeException(String
                        .format("Create new BeanCopier instance of %s failed: %s", targetClass, e.getMessage()));
            }

        }
    }

}
