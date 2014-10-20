/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.tester.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import wi.util.FileUtil;

/**
 *
 * @author hermeschang
 */
public class UrlsConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(UrlsConfig.class);
    private static final double _VERSION = 1.0;
    
    @Since(1.0) @SerializedName("ServerUrl")
    public String mServerUrl;
    @Since(1.0) @SerializedName("ApiList")
    public List<String> mApiList;
    
    private static final String CHARSET = "UTF-8";

    public static UrlsConfig loadFromStream(InputStream in) {
        try {
            String content = FileUtil.readStreamAsString(in, Charset.forName(CHARSET));

            return UrlsConfig.loadFromJson(content);
        } catch (Exception ex) {
            return new UrlsConfig();
        }
    }

    public static UrlsConfig loadFromJson(String json) {
        Gson gson = new GsonBuilder().setVersion(_VERSION).create();
        UrlsConfig config = gson.fromJson(json, UrlsConfig.class);
        config.init(); 
        return config;
    }
    
    private void init() {
    }

}
