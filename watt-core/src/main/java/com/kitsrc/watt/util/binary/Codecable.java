package com.kitsrc.watt.util.binary;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/23
 * @time : 12:37
 * Description:
 */

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class Codecable {

    public static List<FieldWrapper> resolveFileldWrapperList(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<FieldWrapper> fieldWrapperList = new ArrayList<>();
        for (Field field : fields) {
            CodecProprety codecProprety = field.getAnnotation(CodecProprety.class);
            if (codecProprety == null) {
                continue;
            }
            FieldWrapper fw = new FieldWrapper(field, codecProprety);
            fieldWrapperList.add(fw);
        }

        Collections.sort(fieldWrapperList, new Comparator<FieldWrapper>() {
            @Override
            public int compare(FieldWrapper o1, FieldWrapper o2) {
                return o1.getCodecProprety()
                        .order() - o2.getCodecProprety()
                        .order();
            }
        });

        return fieldWrapperList;
    }

    @JsonIgnore
    public abstract List<FieldWrapper> getFieldWrapperList();
}
