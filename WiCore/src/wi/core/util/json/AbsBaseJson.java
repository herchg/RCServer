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
 * @author hermeschang
 */
public abstract class AbsBaseJson {
    
    public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public String toJson(double version) {
        Gson gson = getGson(version);
        return gson.toJson(this);
    }
    
    protected static Gson getGson(double version) {
        return new GsonBuilder().setDateFormat(DATETIME_FORMAT).setVersion(version).create();
    }

    public static AbsBaseJson fromJson(double version, String json, Class c) {
        Gson gson = getGson(version);
        AbsBaseJson data = (AbsBaseJson)gson.fromJson(json, c);
        data.init();
        return data;
    }
    
    abstract public void init();
}
