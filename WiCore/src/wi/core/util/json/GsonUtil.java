/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.core.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author 10307905
 */
public class GsonUtil {
    public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static Gson getGson(double version) {
        return new GsonBuilder().setDateFormat(DATETIME_FORMAT).setVersion(version).create();
    }
    public static Gson getGson() {
        return new GsonBuilder().setDateFormat(DATETIME_FORMAT).create();
    }

}
