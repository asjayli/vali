package com.vali.util.binary;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/23
 * @time : 12:39
 * Description:
 */

import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldWrapper {
    /**
     * 上下行数据属性
     */
    private Field field;
    /**
     * 上下行数据属性上的注解
     */
    private CodecProprety codecProprety;
}
