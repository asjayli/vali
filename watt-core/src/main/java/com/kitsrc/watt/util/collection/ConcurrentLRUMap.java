package com.kitsrc.watt.util.collection;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/4/17 0017
 * @time : 15:43
 * Description:
 */
public class ConcurrentLRUMap<K, V> extends LinkedHashMap<K, V> implements ConcurrentMap<K, V>, Serializable {
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final long serialVersionUID = 4666961887200560335L;
    private final int maxCapacity;
    private final Lock lock = new ReentrantLock();

    public ConcurrentLRUMap(int maxCapacity) {
        super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxCapacity;
    }

    @Override
    public V get(Object key) {
        lock.lock();
        try {

            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        lock.lock();
        try {

            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the
     * specified key.
     *
     * @param key The key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key.
     */
    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     * specified value
     */
    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    /**
     * Copies all of the mappings from the specified map to this map.
     * These mappings will replace any mappings that this map had for
     * any of the keys currently in the specified map.
     *
     * @param m mappings to be stored in this map
     * @throws NullPointerException if the specified map is null
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        lock.lock();
        try {

            super.putAll(m);
        } finally {
            lock.unlock();
        }

    }
}

