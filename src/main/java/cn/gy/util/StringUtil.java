package cn.gy.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author kuyun
 * @date 2018年6月28日
 * @desc
 */
public class StringUtil {

    /**
     * @param str
     * @return boolean
     * @description 判断字符串是否是标准的JSONObject串
     */
    public static boolean isJSONObject(String str) {
        try {
            JSONObject.parseObject(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @param str
     * @return boolean
     * @description 判断字符串是否是标准的JSONArray串
     */
    public static boolean isJSONArray(String str) {
        try {
            JSONArray.parseArray(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static JSONArray subJSONArray(JSONArray arr, int sIdx, int eIdx) {
        JSONArray tempArr = new JSONArray();
        if (arr.isEmpty() || sIdx >= arr.size() || eIdx < 0 || sIdx > eIdx) {
            return tempArr;
        }
        int e = eIdx < arr.size() ? eIdx : arr.size();
        for (int i = sIdx; i <= e; i++) {
            tempArr.add(arr.get(i));
        }
        return tempArr;
    }

    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str.trim())) {
            return false;
        }

        return true;
    }

}
