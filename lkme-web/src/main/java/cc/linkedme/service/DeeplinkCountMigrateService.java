package cc.linkedme.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPipelineReadCallback;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.redis.JedisReadPipeline;
import cc.linkedme.commons.shard.ShardingSupportHash;

import cc.linkedme.dao.webapi.DeepLinkDateCountDao;
import cc.linkedme.data.model.DeepLinkDateCount;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by LinkedME01 on 16/6/23.
 */

@Service
public class DeeplinkCountMigrateService {
    private final static String FILE_PATH = "/data1/count_data/";
    @Resource
    ShardingSupportHash<JedisPort> deepLinkCountShardingSupport;

    @Resource
    DeepLinkDateCountDao deepLinkDateCountDao;

    public void migrateRedisCountDataToMysql(String fileName) {
        if (Strings.isNullOrEmpty(fileName)) {
            ApiLogger.warn(String.format("fileName is null, fileName = %s", fileName));
            return;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(FILE_PATH + fileName));
            String temp;
            int i = 0;
            Map<String, DeepLinkDateCount> deepLinkDateCounts = new HashMap<>(100);
            while ((temp = br.readLine()) != null) {
                String[] lineArr = temp.split("\t");
                if (lineArr.length < 2 || Strings.isNullOrEmpty(lineArr[0]) || Strings.isNullOrEmpty(lineArr[1])) {
                    continue;
                }
                DeepLinkDateCount deepLinkDateCount = new DeepLinkDateCount();
                deepLinkDateCount.setDeeplinkId(Long.parseLong(lineArr[0].split("_")[1]));
                deepLinkDateCount.setAppId(Integer.parseInt(lineArr[1]));
                deepLinkDateCounts.put(lineArr[0], deepLinkDateCount);
                i++;
                if (i == 100) {
                    //导入数据
                    importDataToMysql(deepLinkDateCounts, fileName);

                    // 满100个元素后,把i置为0,重新生成新Map
                    i = 0;
                    deepLinkDateCounts = new HashMap<>(100);
                }
            }

            if (i > 0) {
                importDataToMysql(deepLinkDateCounts, fileName);
            }

        } catch (FileNotFoundException e) {
            ApiLogger.error(String.format("file not found, fileName = %s", fileName), e);
        } catch (IOException e) {
            ApiLogger.error(String.format("migrate data from redis to mysql failed, fileName = %s", fileName), e);
        } catch (Exception e) {
            ApiLogger.error(String.format("migrate data from redis to mysql failed, fileName = %s", fileName), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    ApiLogger.error(String.format("close BufferedReader failed"), e);
                }
            }
        }
    }

    private void importDataToMysql(Map<String, DeepLinkDateCount> deepLinkDateCounts, String date) {
        // 批量获取100个deeplink id的计数
        Map<String, Map<String, String>> multiResult = getDeepLinkCounts(deepLinkDateCounts.keySet().toArray(new String[0]));

        // 解析multiResult,把计数结果插入到mysql
        for (Map.Entry<String, Map<String, String>> entry : multiResult.entrySet()) {
            String key = entry.getKey();
            Map<String, String> counts = entry.getValue();
            DeepLinkDateCount deepLinkDateCount = deepLinkDateCounts.get(key);

            if (!Strings.isNullOrEmpty(counts.get("click"))) {
                deepLinkDateCount.setClick(Long.parseLong(counts.get("click")));
            }

            if (!Strings.isNullOrEmpty(counts.get("open"))) {
                deepLinkDateCount.setOpen(Long.parseLong(counts.get("open")));
            }

            if (!Strings.isNullOrEmpty(counts.get("install"))) {
                deepLinkDateCount.setInstall(Long.parseLong(counts.get("install")));
            }

            if (!Strings.isNullOrEmpty(counts.get("ios_click"))) {
                deepLinkDateCount.setIosClick(Long.parseLong(counts.get("ios_click")));
            }

            if (!Strings.isNullOrEmpty(counts.get("ios_open"))) {
                deepLinkDateCount.setIosOpen(Long.parseLong(counts.get("ios_open")));
            }

            if (!Strings.isNullOrEmpty(counts.get("ios_install"))) {
                deepLinkDateCount.setIosInstall(Long.parseLong(counts.get("ios_install")));
            }

            if (!Strings.isNullOrEmpty(counts.get("adr_click"))) {
                deepLinkDateCount.setAdrClick(Long.parseLong(counts.get("adr_click")));
            }

            if (!Strings.isNullOrEmpty(counts.get("adr_open"))) {
                deepLinkDateCount.setAdrOpen(Long.parseLong(counts.get("adr_open")));
            }

            if (!Strings.isNullOrEmpty(counts.get("adr_install"))) {
                deepLinkDateCount.setAdrInstall(Long.parseLong(counts.get("adr_install")));
            }

            if (!Strings.isNullOrEmpty(counts.get("pc_click"))) {
                deepLinkDateCount.setPcClick(Long.parseLong(counts.get("pc_click")));
            }

            if (!Strings.isNullOrEmpty(counts.get("pc_ios_scan"))) {
                deepLinkDateCount.setPcIosScan(Long.parseLong(counts.get("pc_ios_scan")));
            }

            if (!Strings.isNullOrEmpty(counts.get("pc_ios_open"))) {
                deepLinkDateCount.setPcIosOpen(Long.parseLong(counts.get("pc_ios_open")));
            }

            if (!Strings.isNullOrEmpty(counts.get("pc_ios_install"))) {
                deepLinkDateCount.setPcIosInstall(Long.parseLong(counts.get("pc_ios_install")));
            }

            if (!Strings.isNullOrEmpty(counts.get("pc_adr_scan"))) {
                deepLinkDateCount.setPcAdrScan(Long.parseLong(counts.get("pc_adr_scan")));
            }

            if (!Strings.isNullOrEmpty(counts.get("pc_adr_open"))) {
                deepLinkDateCount.setPcAdrOpen(Long.parseLong(counts.get("pc_adr_open")));
            }

            if (!Strings.isNullOrEmpty(counts.get("pc_adr_install"))) {
                deepLinkDateCount.setPcAdrInstall(Long.parseLong(counts.get("pc_adr_install")));
            }

        }
        deepLinkDateCountDao.addDeepLinksDateCounts(date, deepLinkDateCounts.values().toArray(new DeepLinkDateCount[0]));
    }

    private Map<String, Map<String, String>> getDeepLinkCounts(String[] keys) {
        Map<String, Map<String, String>> multiResult = new HashMap<>();
        Map<Integer, List<String>> dbIdsMap = deepLinkCountShardingSupport.getDbSharding(keys);
        for (Map.Entry<Integer, List<String>> entry : dbIdsMap.entrySet()) {
            // TODO 后续可以改成多线程调用
            int db = entry.getKey();
            List<String> keyList = entry.getValue();
            JedisPort client = deepLinkCountShardingSupport.getClientByDb(db);
            List<Object> list = client.pipeline(new JedisPipelineReadCallback() {
                @Override
                public void call(JedisReadPipeline pipeline) {
                    for (String key : keyList) {
                        pipeline.hgetAll(key);
                    }
                }
            });

            if (CollectionUtils.isEmpty(list)) {
                continue;
            }

            for (int i = 0; i < keyList.size(); i++) {
                Map<String, String> countMap = (Map<String, String>) list.get(i);
                if (CollectionUtils.isEmpty(countMap)) {
                    continue;
                }
                multiResult.put(keyList.get(i), countMap);
            }
        }
        return multiResult;
    }
}
