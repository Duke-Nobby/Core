package com.zkcb.doctorstation.util;

/**
 * Created by shy on 2016/12/16.
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 把Hshmap转换成json，拼装后以html格式输出
 *
 */
public class HashmapToJson {
    /**把数据源HashMap转换成json
     * @param map
     */
    public static String hashMapToJson(HashMap map) {
        String string = "{";
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Entry e = (Entry) it.next();
            string += "'" + e.getKey() + "':";
            string += "'" + e.getValue() + "',";
        }
        string = string.substring(0, string.lastIndexOf(","));
        string += "}";
        return string;
    }
}
