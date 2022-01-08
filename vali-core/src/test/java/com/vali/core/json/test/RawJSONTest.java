package com.vali.core.json.test;


import com.alibaba.fastjson.JSON;
import com.vali.core.ObjectUtil;
import com.vali.core.json.JSONUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/1/27
 * @time : 12:45
 * Description:
 */
public class RawJSONTest {
    @Test
    public void testResponseTO() {
        String str3 = "{\"code\":201001,\"data\":{\"msg\":\"{com.baosight.tlerp.crm.validator.constraints.OperateCustomerClue" +
                      ".noData" +
                      ".message}!{com.baosight.tlerp.crm.validator.constraints.CustomerApplicationCompanyName.message}!\"," +
                      "\"pkId\":\"\",\"flag\":false,\"status\":false,\"tipsCode\":\"\"},\"message\":\"{com.baosight.tlerp.crm" +
                      ".validator.constraints.OperateCustomerClue.noData.message}!{com.baosight.tlerp.crm.validator.constraints" +
                      ".CustomerApplicationCompanyName.message}!\",\"success\":false}";
        final ResponseTO responseTO = JSON.parseObject(str3, ResponseTO.class);
        final ResponseTO responseTO2 = JSONUtil.parseObject(str3, ResponseTO.class);
        final ResponseTO responseTO3 = JSONUtil.parseObject(str3, ResponseTO.class);
    }

    @Test
    public void testEscape() {
        String sourceText = "{\"success\":true,\"code\":200,\"message\":\"OK\"," +
                            "\"data\":\"\\\"\\\\\\\"{\\\\\\\\\\\\\\\"count\\\\\\\\\\\\\\\":1," +
                            "\\\\\\\\\\\\\\\"list\\\\\\\\\\\\\\\":[{\\\\\\\\\\\\\\\"bufferDesc" +
                            "\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"周边地区发运\\\\\\\\\\\\\\\"," +
                            "\\\\\\\\\\\\\\\"bufferName\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"常规发运\\\\\\\\\\\\\\\"," +
                            "\\\\\\\\\\\\\\\"customerId\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"33c2de7e69d911e9a798525400b4f4ab" +
                            "\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"deletable\\\\\\\\\\\\\\\":36," +
                            "\\\\\\\\\\\\\\\"flagDeletable\\\\\\\\\\\\\\\":36," +
                            "\\\\\\\\\\\\\\\"id\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"2315ddc8b56040d4b02d7dd455377e3a\\\\\\\\\\\\\\\"," +
                            "\\\\\\\\\\\\\\\"status\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"33\\\\\\\\\\\\\\\"," +
                            "\\\\\\\\\\\\\\\"timeBuffer\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"5\\\\\\\\\\\\\\\"," +
                            "\\\\\\\\\\\\\\\"timeBufferMillis\\\\\\\\\\\\\\\":432000000}]," +
                            "\\\\\\\\\\\\\\\"pageNum\\\\\\\\\\\\\\\":1," +
                            "\\\\\\\\\\\\\\\"pageSize\\\\\\\\\\\\\\\":10000," +
                            "\\\\\\\\\\\\\\\"token\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\"," +
                            "\\\\\\\\\\\\\\\"traceId\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\"}\\\\\\\"\\\"\"," +
                            "\"traceId\":\"f6f757c88ac14349b5624f3f6baea0b1\",\"sha256\":\"\"}";
        String unescapeJson = StringEscapeUtils.unescapeJson(sourceText);
        System.out.println(unescapeJson);

    }

    @Test
    public void testMessageTO() {
        Object jsonObj = "{\"actionName\":\"testResp\",\"data\":\"2333\",\"serviceName\":\"exampleService\"," +
                         "\"tk\":\"40f9959eab89dda74f1404a717b6d1b8\",\"token\":\"f2792cd56dc94e3498ce018c662728b6\"}";
        Object jsonObj2 = "{\"actionName\":\"closeSupplierClues\"," +
                          "\"data\":\"{\\\"supplierClueIds\\\":[\\\"e47d4fbfecfb80ac708f76b995b6873d\\\"]}\"," +
                          "\"serviceName\":\"djcrmSupplierClueService\",\"tk\":\"dd7e37d9f3287cd89ccb736c69074e26\"," +
                          "\"token\":\"ad958030289843138a311926a89222c1\"}";
        String str3 = "{\"code\":201001,\"data\":{\"msg\":\"{com.baosight.tlerp.crm.validator.constraints.OperateCustomerClue" +
                      ".noData" +
                      ".message}!{com.baosight.tlerp.crm.validator.constraints.CustomerApplicationCompanyName.message}!\"," +
                      "\"pkId\":\"\",\"flag\":false,\"status\":false,\"tipsCode\":\"\"},\"message\":\"{com.baosight.tlerp.crm" +
                      ".validator.constraints.OperateCustomerClue.noData.message}!{com.baosight.tlerp.crm.validator.constraints" +
                      ".CustomerApplicationCompanyName.message}!\",\"success\":false}";

        String s1 = StringEscapeUtils.unescapeJson(jsonObj.toString());
        String s2 = StringEscapeUtils.unescapeJson(jsonObj2.toString());
        final String escapeJson = StringEscapeUtils.escapeJson(str3);

        String r1 = "\"\\{";
        String r2 = "\\}\"";
        //s1=s1.replaceAll(r1, "{");
        //s1=s1.replaceAll(r2, "}");


        String jsonString = JSONUtil.toJSONString(jsonObj);

        MessageTO messageTO = JSONUtil.parseObject(jsonString, MessageTO.class);
        System.out.println(JSONUtil.toJSONString(messageTO));
        //s2=s2.replaceAll(r1, "{");
        //s2=s2.replaceAll(r2, "}");
        String jsonString2 = JSONUtil.toJSONString(s2);
        MessageTO messageTO2 = JSONUtil.parseObject(jsonString2, MessageTO.class);
        System.out.println(JSONUtil.toJSONString(messageTO2));
    }

    @Test
    public void testToJSONString() {
        //Boolean b = true;
        // 基本类型
        // 非法字符串
        String text = "0123";
        String text1 = "true";
        String text2 = "\"xxxxxxx\"";
        String text3 = "{}";
        String text4 = "[]";
        String jsonString = JSONUtil.toJSONString(text);
        String jsonString1 = JSONUtil.toJSONString(text1);
        String jsonString2 = JSONUtil.toJSONString(text2);
        String jsonString3 = JSONUtil.toJSONString(text3);
        String jsonString4 = JSONUtil.toJSONString(text4);
        System.out.println(jsonString);
        System.out.println(jsonString1);
        System.out.println(jsonString2);
        System.out.println(jsonString3);
        System.out.println(jsonString4);

        //JSONUtil.toJSON(text1);
        //JSONUtil.toJSON(text2);
        //JSONUtil.toJSON(text3);
    }

    @Test
    public void testToJSON() {
        String text = "0123";
        String text1 = "true";
        String text2 = "\"xxxxxxx\"";
        String text3 = "{}";
        String text4 = "[]";
        Object parse = JSONUtil.parse(text);
        Object parse1 = JSONUtil.parse(text1);
        Object parse2 = JSONUtil.parse(text2);
        Object parse3 = JSONUtil.parse(text3);
        Object parse4 = JSONUtil.parse(text4);
        System.out.println(ObjectUtil.isPrimitive(parse));
        System.out.println(ObjectUtil.isPrimitive(parse1));
        System.out.println(ObjectUtil.isPrimitive(parse2));
        System.out.println(ObjectUtil.isPrimitive(parse3));
        System.out.println(ObjectUtil.isPrimitive(parse4));

        if (parse instanceof JSON) {
            System.out.println("000");

        }
        if (parse1 instanceof JSON) {
            System.out.println("111");
        }
        if (parse2 instanceof JSON) {
            System.out.println("222");
        }
        if (parse3 instanceof JSON) {
            System.out.println("333");
        }
        if (parse4 instanceof JSON) {
            System.out.println("444");
        }
        String jsonString = JSONUtil.toJSONString(parse);
        String jsonString1 = JSONUtil.toJSONString(parse1);
        String jsonString2 = JSONUtil.toJSONString(parse2);
        String jsonString3 = JSONUtil.toJSONString(parse3);
        String jsonString4 = JSONUtil.toJSONString(parse4);
        Long aLong = JSON.parseObject(jsonString, Long.class);
        Boolean aBoolean = JSON.parseObject(jsonString, Boolean.class);
        String xxxxStr = JSON.parseObject(jsonString, String.class);
        System.out.println(jsonString);
        System.out.println(jsonString1);
        System.out.println(jsonString2);
        System.out.println(jsonString3);
        System.out.println(jsonString4);


    }

    @Test
    public void testJSON() {
        String text = "0123";
        String text1 = "true";
        String text2 = "\"xxxxxxx\"";
        String text3 = "{}";
        String text4 = "[]";
        Object parse = JSON.parse(text);
        Object parse1 = JSON.parse(text1);
        Object parse2 = JSON.parse(text2);
        Object parse3 = JSON.parse(text3);
        Object parse4 = JSON.parse(text4);
        final Object json = JSON.toJSON(text);
        final Object json1 = JSON.toJSON(text1);
        final Object json2 = JSON.toJSON(text2);
        final Object json3 = JSON.toJSON(text3);
        final Object json4 = JSON.toJSON(text4);
        final String jsonString = JSON.toJSONString(text);
        final String jsonString1 = JSON.toJSONString(text1);
        final String jsonString2 = JSON.toJSONString(text2);
        final String jsonString3 = JSON.toJSONString(text3);
        final String jsonString4 = JSON.toJSONString(text4);
        System.out.println();
    }
}
