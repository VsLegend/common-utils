package com.utils.common;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author Wang Junwei
 * @Date 2022/6/20 14:19
 * @Description 转换
 */
public class ConvertUtils {


    /**
     * 对象转阿里json
     * @param obj
     * @return
     */
    public static JSONObject obj2Json(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        String jsonString = JSONObject.toJSONString(obj);
        return JSONObject.parseObject(jsonString);
    }

}
