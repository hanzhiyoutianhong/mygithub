package cc.linkedme.offline_calculation;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.util.Util;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by LinkedME01 on 16/4/21.
 */
public class LogHandler {
    public void statisticsDeepLinkCount() {
        BufferedReader br = null;
        String fileName = "";
        try {
            for (int i = 0; i < 24; i++) {
                if (i < 10) {
                    fileName = "/data1/tomcat/logs/biz.log.20160421-0" + i;
                } else {
                    fileName = "/data1/tomcat/logs/biz.log.20160421-" + i;
                }
                br = new BufferedReader(new FileReader(fileName));

                String temp = null;
                String[] fields = null;
                while ((temp = br.readLine()) != null) {
                    fields = temp.split("\t");

                }

            }
        } catch (FileNotFoundException e) {
            ApiLogger.error(String.format("FeedControlParamDao.getReader error, file: %s is not found", fileName), e);
        } catch (IOException e) {
            ApiLogger.error("readline failed");
        }
    }
}
