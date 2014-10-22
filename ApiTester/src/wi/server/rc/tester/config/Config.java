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
import wi.server.rc.tester.Constants;
import wi.server.rc.tester.config.data.ApiInfo;
import wi.server.util.FileUtil;

/**
 *
 * @author hermeschang
 */
public class Config {
    
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final double _VERSION = 1.0;
    
    @Since(1.0) @SerializedName("ApiInfoList")
    public List<ApiInfo> mApiInfoList;
    @Since(1.0) @SerializedName("Desc")
    protected transient String mDesc;

    public static Config loadFromStream(InputStream in) {
        try {
            String content = FileUtil.readStreamAsString(in, Charset.forName(Constants.CHARSET));

            return Config.loadFromJson(content);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Config();
        }
    }

    public static Config loadFromJson(String json) {
        Gson gson = new GsonBuilder().setVersion(_VERSION).create();
        Config config = gson.fromJson(json, Config.class);
        config.init(); 
        return config;
    }
    
    private void init() {
        // do nothing
    }

}
