package com.vali.bo;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * validator 校验结果封装BO
 *
 * @author : LiJie
 * @date : 2019/1/28
 * @time : 9:33
 * Description:
 */
@Getter
@Setter
@Accessors(chain = true)
public class VerificationBO implements Serializable {
    private static final long serialVersionUID = -1485722399935660305L;
    private String propertyPath;
    private String message;
    private Object invalidValue;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("'message':'").append(message).append("',");
        sb.append("'invalidValue':'").append(invalidValue).append("',");
        sb.append("'fieldPath':'").append(propertyPath).append("'");
        sb.append('}');
        return sb.toString();
    }
}
