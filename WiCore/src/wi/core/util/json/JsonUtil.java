/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.core.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hermeschang
 */
public class JsonUtil {
    
    /**
     * for select several rows
     * @param rs ResultSet from SQL Statement
     * @return JSONElement a Array of JsonElement
     */
    public static JsonElement toJsonArray(ResultSet rs) {
        try {
            
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            ResultSetMetaData metaData = rs.getMetaData();
            
            while (rs.next()) {
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    map.put(columnLabel, rs.getObject(columnLabel));
                }
                result.add(map);
            }
            // to json
            Gson gson = GsonUtil.getGson();
            return gson.toJsonTree(result);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * for select one row
     * @param rs ResultSet from SQL Statement
     * @return JSONElement return a JsonElement
     */

    public static JsonElement toJsonElement(ResultSet rs) {
        try {
            
            Map<String, Object> result = new LinkedHashMap<String, Object>();
            ResultSetMetaData metaData = rs.getMetaData();
            if (rs.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    result.put(columnLabel, rs.getObject(columnLabel));
                }
            }
            // to json
            Gson gson = GsonUtil.getGson();
            return gson.toJsonTree(result);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static Map<?, ?> toMap(String jsonString) {
        
        Gson gson = GsonUtil.getGson();
        try {
            return gson.fromJson(jsonString, LinkedTreeMap.class);
        } catch (Exception ex) {
        }
        return null;
    }
    
    public static List<?> toArray(String jsonString) {
        
        Gson gson = GsonUtil.getGson();
        try {
            return gson.fromJson(jsonString, ArrayList.class);
        } catch (Exception ex) {
        }
        return null;
    }

}
