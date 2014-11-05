/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.core.util.json;

import com.google.gson.Gson;

/**
 *
 * @author hermeschang
 */
public abstract class AbsBaseJson {
    
    public String toJson(double version) {
        Gson gson = GsonUtil.getGson(version);
        return gson.toJson(this);
    }

    public static AbsBaseJson fromJson(double version, String json, Class c) {
        Gson gson = GsonUtil.getGson(version);
        AbsBaseJson data = (AbsBaseJson)gson.fromJson(json, c);
        data.init();
        return data;
    }
    
    abstract public void init();
}
