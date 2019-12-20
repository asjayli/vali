package com.kitsrc.watt.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.constraints.NotNull;

import net.sf.cglib.core.Predicate;
import net.sf.cglib.core.Transformer;

/**
 * 集合操作工具类
 */
public abstract class CollectionUtil {

    public static final String KV_SPLIT = "=";
    public static final String PAIR_SPLIT = "&";
    /**
     * Constant to avoid repeated object creation
     */
    private static final Integer INTEGER_ONE = 1;

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Return {@code true} if the supplied Map is {@code null} or empty.
     * Otherwise, return {@code false}.
     *
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
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

    /**
     * Is size equals boolean.
     *
     * @param col0 the col 0
     * @param col1 the col 1
     * @return the boolean
     */
    public static boolean isSizeEquals(Collection col0, Collection col1) {
        if (col0 == null) {
            return col1 == null;
        }
        else {
            if (col1 == null) {
                return false;
            }
            else {
                return col0.size() == col1.size();
            }
        }
    }

    /**
     * Encode map to string
     *
     * @param map origin map
     * @return String
     */
    public static String encodeMap(Map<String, String> map) {
        return encodeMap(map, KV_SPLIT, PAIR_SPLIT);
    }

    /**
     * Encode map to string
     *
     * @param map
     * @param kvSplit
     * @param pairSplit
     * @return
     */
    public static String encodeMap(Map<String, String> map, String kvSplit, String pairSplit) {
        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return StringUtil.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append(kvSplit)
                    .append(entry.getValue())
                    .append(pairSplit);
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * Decode string to map
     *
     * @param data data
     * @return map
     */
    public static Map<String, String> decodeMap(String data) {
        return decodeMap(data, KV_SPLIT, PAIR_SPLIT);
    }

    /**
     * Decode string to map
     *
     * @param data
     * @param kvSplit
     * @param pairSplit
     * @return
     */
    public static Map<String, String> decodeMap(String data, String kvSplit, String pairSplit) {
        if (data == null) {
            return null;
        }

        if (StringUtil.isBlank(data)) {
            return Collections.<String, String>emptyMap();
        }
        String[] kvPairs = data.split(pairSplit);
        if (kvPairs.length == 0) {
            return Collections.<String, String>emptyMap();
        }
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(kvPairs.length, 1F);
        for (String kvPair : kvPairs) {
            if (StringUtil.isBlank(kvPair)) {
                continue;
            }
            String[] kvs = kvPair.split(kvSplit);
            if (kvs.length != 2) {
                continue;
            }
            map.put(kvs[0], kvs[1]);
        }
        return map;
    }

    /**
     * Returns a new {@link Collection} containing <tt><i>a</i> - <i>b</i></tt>. The cardinality of each element
     * <i>e</i> in the returned {@link Collection} will be the cardinality of <i>e</i> in <i>a</i> minus the cardinality
     * of <i>e</i> in <i>b</i>, or zero, whichever is greater.
     *
     * @param minuendCollection    the collection to subtract from, must not be null
     * @param subtrahendCollection the collection to subtract, must not be null
     * @return a new collection with the results
     * @see Collection#removeAll
     */
    public static <T> Collection<T> subtract(@NotNull final Collection<T> minuendCollection,
            @NotNull final Collection<T> subtrahendCollection) {
        ArrayList<T> minuendList = new ArrayList<>(minuendCollection);
        Iterator<T> subtrahendIterator = subtrahendCollection.iterator();
        synchronized (minuendList) {
            while (subtrahendIterator.hasNext()) {
                T next = subtrahendIterator.next();
                minuendList.remove(next);
            }
        }
        return minuendList;
    }

    /**
     * Returns a {@link Map} mapping each unique element in the given {@link Collection} to an {@link Integer}
     * representing the number of occurrences of that element in the {@link Collection}.
     * <p>
     * Only those elements present in the collection will appear as keys in the map.
     *
     * @param coll the collection to get the cardinality map for, must not be null
     * @return the populated cardinality map  key: 原集合对象  value ： 重复计数
     */
    public static Map<Object, Integer> getCardinalityMap(final Collection coll) {
        Map<Object, Integer> count = new HashMap<>(coll.size());
        Iterator iterator = coll.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            Integer c = count.get(next);
            if (c == null) {
                count.put(next, INTEGER_ONE);
            }
            else {
                count.put(next, c + 1);
            }
        }
        return count;
    }

    /**
     * Returns <tt>true</tt> iff the given {@link Collection}s contain exactly the same elements with exactly the same
     * cardinalities.
     * <p>
     * That is, iff the cardinality of <i>e</i> in <i>a</i> is equal to the cardinality of <i>e</i> in <i>b</i>, for
     * each element <i>e</i> in <i>a</i> or <i>b</i>.
     *
     * @param a the first collection, must not be null
     * @param b the second collection, must not be null
     * @return <code>true</code> iff the collections contain the same elements with the same cardinalities.
     */
    public static boolean isEqualCollection(final Collection a, final Collection b) {
        //比较 集合容量
        if (a.size() != b.size()) {
            return false;
        }
        else {
            Map<Object, Integer> mapa = getCardinalityMap(a);
            Map<Object, Integer> mapb = getCardinalityMap(b);
            // 比较集合元素 基数
            if (mapa.size() != mapb.size()) {
                return false;
            }
            else {
                Iterator it = mapa.keySet()
                        .iterator();
                while (it.hasNext()) {
                    Object obj = it.next();
                    // 比较集合中 各元素的 对应重复计数
                    if (getFreq(obj, mapa) != getFreq(obj, mapb)) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    /**
     * 返回该类型 map 中的value 根据key  如果值不存在则返回 -1
     *
     * @param key
     * @param freqMap
     * @return
     */
    private static int getFreq(final Object key, final Map<Object, Integer> freqMap) {
        Integer count = freqMap.get(key);
        if (count != null) {
            return count.intValue();
        }
        return -1;
    }

    public static Map bucket(Collection c, Transformer t) {
        Map buckets = new HashMap();
        for (Iterator it = c.iterator(); it.hasNext(); ) {
            Object value = (Object) it.next();
            Object key = t.transform(value);
            List bucket = (List) buckets.get(key);
            if (bucket == null) {
                buckets.put(key, bucket = new LinkedList());
            }
            bucket.add(value);
        }
        return buckets;
    }

    public static void reverse(Map source, Map target) {
        for (Iterator it = source.keySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            target.put(source.get(key), key);
        }
    }

    public static Collection filter(Collection c, Predicate p) {
        Iterator it = c.iterator();
        while (it.hasNext()) {
            if (!p.evaluate(it.next())) {
                it.remove();
            }
        }
        return c;
    }

    public static List transform(Collection c, Transformer t) {
        List result = new ArrayList(c.size());
        for (Iterator it = c.iterator(); it.hasNext(); ) {
            result.add(t.transform(it.next()));
        }
        return result;
    }

    public static Map getIndexMap(List list) {
        Map indexes = new HashMap();
        int index = 0;
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            indexes.put(it.next(), new Integer(index++));
        }
        return indexes;
    }
}
