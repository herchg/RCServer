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
    
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
