package com.javan.util;

import java.lang.reflect.Method;
import java.util.List;

public class JsonUtil {
	
	//list转换为json，参数String[] fields为要转换的字段，List<?> data为待转换list
    public static String listToLayJson(String[] fields, List<?> data) throws Exception {
        StringBuilder re = new StringBuilder();
        re.append("[{\"status\":0}, {\"message\": \"成功\" }, {\"count\": 1000},{\"rows\":{\"item\":[");
        for (Object object : data) {
            re.append(beanToJson(fields, object));
            re.append(',');
        }
        re.deleteCharAt(re.length() - 1);
        re.append("]}}]");
        return re.toString();
    }
    
    public static String listToJson(String[] fields, List<?> data) throws Exception {
        StringBuilder re = new StringBuilder();
        re.append("{\"code\":\"0000\",\"message\":\"成功\",\"data\":[");
        for (Object object : data) {
            re.append(beanToJson(fields, object));
            re.append(',');
        }
        re.deleteCharAt(re.length() - 1);
        re.append("]}");
        return re.toString();
    }
    
    //对象转换为json，参数String[] fields为要转换的字段，Object object为待转换的对象
    public static String objectToJson(String[] fields, Object object) throws Exception {
        return "{\"code\":\"0000\",\"message\":\"成功\",\"data\":" +
                beanToJson(fields, object) +
                '}';
    }

    //将JavaBean对象转换为Json格式，参数String[] fields为要转换的字段，Object object为待转换的对象
    public static String beanToJson(String[] fields, Object object) throws Exception {
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append('{');
        for (String field : fields) {
            jsonStr.append('\"').append(field).append("\":\"");
            //根据传入的字段列表拼接get方法名
            String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
            Method method = object.getClass().getDeclaredMethod(methodName);
            //通过反射获取数据
            Object tmp = method.invoke(object);
            String str;
            //判空
            if (tmp != null) {
                str = tmp.toString();
            } else {
                str = "NULL";
            }
            //转义数据中的双引号，防止破坏json格式
            if (str.contains("\"")) {
                char[] arr = str.toCharArray();
                for(char c : arr) {
                	if(c == '\"') {
                		jsonStr.append('\\');
                	}
                	jsonStr.append(c);
                }
            }else {
            	jsonStr.append(str);
            }
            jsonStr.append("\",");
        }
        //删除多余逗号
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append('}');
        return jsonStr.toString();
    }
    
}
