/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.tester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import wi.core.pattern.IExecutable;
import wi.server.rc.tester.config.Config;
import wi.server.rc.tester.config.data.ApiInfo;

/**
 *
 * @author hermeschang
 */
public class RCAPITester implements IExecutable {

    private static final Logger logger = LoggerFactory.getLogger(RCAPITester.class);

    protected Config mConfig;
    protected int mThreadNum;
    protected int mCount;

    public RCAPITester() {
    }

    protected void loadConfig(String path) {
        try {
            mConfig = Config.loadFromStream(new FileInputStream(path));
        } catch (Exception ex) {
            logger.error("loadConfig() exception={}", ex);
        }

    }

    @Override
    public void execute() {

        for (int i = 0; i < mThreadNum; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < mCount; j++) {
                        
                        // HttpClient
                        // 
                        CloseableHttpClient httpClient = HttpClients.createDefault();

                        for (ApiInfo apiInfo : mConfig.mApiInfoList) {
                            
                            logger.info(apiInfo.toJson());
                            System.out.println("api ddd");
                            
                            switch (apiInfo.mMethod) {
                                case Get: {
                                    try {
                                        HttpGet httpGet = new HttpGet(apiInfo.mServerUrl);
                                        CloseableHttpResponse response1 = httpClient.execute(httpGet);
                                        try {
                                            System.out.println(response1.getStatusLine());
                                            HttpEntity entity1 = response1.getEntity();
                                            // do something useful with the response body
                                            // and ensure it is fully consumed
                                            EntityUtils.consume(entity1);
                                        } finally {
                                            response1.close();
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                    
                                    
                                }
                                    break;
                                case Post: {
                                    
                                }
                                    break;
                                case Put: {
                                    
                                }
                                    break;
                                case Delete: {
                                    
                                }
                                    break;
                                    
                            }
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

            tester.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
