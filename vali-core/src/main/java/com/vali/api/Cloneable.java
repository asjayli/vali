package com.vali.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lombok.Cleanup;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/4/17 0017
 * @time : 15:16
 * Description:
 */
public interface Cloneable extends java.lang.Cloneable {

    /**
     * Object 浅拷贝
     *
     * @return
     * @throws CloneNotSupportedException
     */
    Object clone();

    /**
     * 基于流序列化实现的深拷贝
     * @param object
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    default <T> T clone(T object) throws IOException, ClassNotFoundException {

        //分配内存，写入原始对象，生成新对象
        @Cleanup ByteArrayOutputStream bos = new ByteArrayOutputStream();
        @Cleanup ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        @Cleanup ByteArrayInputStream ios = new ByteArrayInputStream(bos.toByteArray());
        @Cleanup ObjectInputStream ois = new ObjectInputStream(ios);
        return (T) ois.readObject();
    }

}
