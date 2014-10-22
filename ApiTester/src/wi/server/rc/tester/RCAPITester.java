/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.tester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import wi.server.core.pattern.IExecutable;
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
                        for (ApiInfo apiInfo : mConfig.mApiInfoList) {
                            logger.info(apiInfo.toString());
                            System.out.println("api ddd");
//                            HttpGet mHttpGet = new HttpGet(mConfig.mServerUrl + "/" + api);
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
