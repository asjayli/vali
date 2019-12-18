package com.kitsrc.watt.core;

import java.io.*;
import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/24
 * @time : 20:04
 * Description:
 */
public class SerializationUtil {
    private static final Logger logger = LoggerFactory.getLogger(SerializationUtil.class);

    /**
     * 将byte数组转化为Object对象
     *
     * @return
     */
    public static Object toObject(byte[] bytes) throws IOException {
        Object object = null;
        @Cleanup ByteArrayInputStream byteArrayInputStream = null;
        @Cleanup ObjectInputStream objectInputStream = null;
        try {
            // 创建ByteArrayInputStream对象
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            // 创建ObjectInputStream对象
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            // 从objectInputStream流中读取一个对象
            object = objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
            return object;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
        return object;
    }

    /**
     * Serialize the given object to a byte array.
     *
     * @param object the object to serialize
     * @return an array of bytes representing the object in a portable fashion
     */

    public static byte[] serialize( Object object) throws IOException {
        if (object == null) {
            return null;
        }
        @Cleanup ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            @Cleanup ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }

    /**
     * Deserialize the byte array into an object.
     *
     * @param bytes a serialized object
     * @return the result of deserializing the bytes
     */

    public static Object deserialize( byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        try {
            @Cleanup ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to deserialize object", ex);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Failed to deserialize object type", ex);
        }
    }

}
