package com.kitsrc.jutils.core.test.bean;

import java.sql.Timestamp;

import com.kitsrc.jutils.core.bean.BeanUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BeanUtilTests {

    @BeforeEach
    public void setUp() {
        System.out.println(System.currentTimeMillis());
    }

    @AfterEach
    public void tearDown() {
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void copyProperties() {
        FromBO fromBO = new FromBO();
        Short shortValue = 1;
        fromBO.setAShort(shortValue);
        fromBO.setAInteger(2);
        fromBO.setALong(3L);
        fromBO.setAFloat(0.1F);
        fromBO.setADouble(0.2D);
        fromBO.setTimestampA(new Timestamp(System.currentTimeMillis()));
        fromBO.setTimestampB(new Timestamp(System.currentTimeMillis()));
        fromBO.setAUtilDate(new java.util.Date());
        fromBO.setBUtilDate(new java.util.Date());
        ToBO toBO = BeanUtil.copyProperties(fromBO, ToBO.class, true);
        System.out.println(toBO);

    }

    @Test
    public void testDouble() {
        FromBO fromBO = new FromBO();
        fromBO.setADouble(0.2D);
        Object doubleValue = 0.2;
        Object sourceFieldValue = fromBO.getADouble();
        if (sourceFieldValue instanceof Float || sourceFieldValue instanceof Double) {
            System.out.println(true);
        }
        else {
            System.out.println(false);
        }
    }

    @Test
    public void copyList() {
    }
}

@Setter
@Getter
@ToString
class FromBO {
    private Short aShort;
    private Integer aInteger;
    private Long aLong;
    private Float aFloat;
    private Double aDouble;
    private Timestamp timestampA;
    private Timestamp timestampB;
    private java.util.Date aUtilDate;
    private java.util.Date bUtilDate;

}

@Setter
@Getter
@ToString
class ToBO {
    private short aShort;
    private int aInteger;
    private long aLong;
    private float aFloat;
    private String aDouble;
    private java.util.Date timestampA;
    private java.sql.Date timestampB;
    private Timestamp aUtilDate;
    private java.sql.Date bUtilDate;
}