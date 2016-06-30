package cc.linkedme.service;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPipelineReadCallback;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.redis.JedisReadPipeline;
import cc.linkedme.commons.shard.ShardingSupportHash;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LinkedME01 on 16/6/23.
 */
public class BtnCountMigrateService {
    @Resource
    ShardingSupportHash<JedisPort> btnCountShardingSupport;

    public void migrateRedisCountDataToMysql(String fileName) {
//        if (Strings.isNullOrEmpty(fileName)) {
//            ApiLogger.warn(String.format("fileName is null, fileName = %s", fileName));
//            return;
//        }
//        BufferedReader br = null;
//        int appId = 0;
//        String btnId;
//        int consumerAppId = 0;
//        String date;
//
//        String[] keys = new String[50];
//        try {
//            br = new BufferedReader(new FileReader(fileName));
//
//            String temp;
//            int i = 0;
//            while ((temp = br.readLine()) != null) {
//                if (i < 50) {
//                    keys[i] = temp;
//                } else {
//                    Map<Integer, List<String>> dbIdsMap = btnCountShardingSupport.getDbSharding(keys);
//                    for (Map.Entry<Integer, List<String>> entry : dbIdsMap.entrySet()) {
//                        // TODO 后续可以改成多线程调用
//                        int db = entry.getKey();
//                        List<String> keyList = entry.getValue();
//                        JedisPort client = btnCountShardingSupport.getClientByDb(db);
//                        List<Object> list = client.pipeline(new JedisPipelineReadCallback() {
//                            @Override
//                            public void call(JedisReadPipeline pipeline) {
//                                for (String key : keyList) {
//                                    pipeline.hgetAll(key);
//                                }
//                            }
//                        });
//                    }
//                }
//                i++;
//
//            }
//
//        } catch (FileNotFoundException e) {
//            ApiLogger.error(String.format("", fileName), e);
//        } catch (IOException e) {
//            ApiLogger.error(String.format("", fileName), e);
//        } catch (Exception e) {
//
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    ApiLogger.error(String.format("", fileName), e);
//                }
//            }
//        }
    }
}
