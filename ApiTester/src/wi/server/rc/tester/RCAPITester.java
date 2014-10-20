/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.tester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.FileInputStream;
import org.apache.http.client.methods.HttpGet;
import wi.server.core.pattern.IExecutable;
import wi.server.rc.tester.config.UrlsConfig;

/**
 *
 * @author hermeschang
 */
public class RCAPITester implements IExecutable {
    
    private static final Logger logger = LoggerFactory.getLogger(RCAPITester.class);

    protected UrlsConfig mConfig;
    protected int mThreadNum;
    protected int mCount;
    
    public RCAPITester() {
    }

    protected void loadConfig(String path) {
        try {
            mConfig = UrlsConfig.loadFromStream(new FileInputStream(path));
        } catch (Exception ex) {
            logger.error("RCAPITester.loadConfig() exception={}", ex);
        }
        
    }
    
    public void execute() {
        
        for (int i = 0; i < mThreadNum; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < mCount; j++) {
                        for (String api: mConfig.mApiList) {
                            HttpGet mHttpGet = new HttpGet(mConfig.mServerUrl + "/" + api);
                        }
                    }
                }
            });
            thread.start();
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        RCAPITester tester = new RCAPITester();
        
        // args
        // args[0] := thread
        // args[1] := count
        // args[2] := urls config path
        try {
            int threadNum = Integer.parseInt(args[0]);
            int count = Integer.parseInt(args[1]);
            String configPath = args[2];
            
            tester.mThreadNum = threadNum;
            tester.mCount = count;
            
            tester.loadConfig(configPath);
            
            tester.executeTest();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

}
