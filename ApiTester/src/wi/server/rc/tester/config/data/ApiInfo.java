/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.tester.config.data;

import wi.server.rc.tester.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import java.io.InputStream;
import java.nio.charset.Charset;
import wi.server.util.FileUtil;

/**
 *
 * @author hermeschang
 */
public class ApiInfo {

    private static final Logger logger = LoggerFactory.getLogger(ApiInfo.class);
    private static final double _VERSION = 1.0;

    @Since(1.0) @SerializedName("ApiName")
    public String mApiName;

    public static ApiInfo loadFromStream(InputStream in) {
        try {
            String content = FileUtil.readStreamAsString(in, Charset.forName(Constants.CHARSET));

            return ApiInfo.loadFromJson(content);
        } catch (Exception ex) {
            return new ApiInfo();
        }
    }

    public static ApiInfo loadFromJson(String json) {
        Gson gson = new GsonBuilder().setVersion(_VERSION).create();
        ApiInfo config = gson.fromJson(json, ApiInfo.class);
        config.init();
        return config;
    }
    
    private void init() {
        
    }
}
