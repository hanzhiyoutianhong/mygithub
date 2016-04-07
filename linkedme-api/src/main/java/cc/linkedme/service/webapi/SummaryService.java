package cc.linkedme.service.webapi;

import cc.linkedme.commons.counter.component.CountComponent;
import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.data.dao.util.DateDuration;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.DeepLinkCount;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LinkedME01 on 16/3/20.
 */

@Service
public class SummaryService {

    @Resource
    DeepLinkDao deepLinkDao;

    @Resource
    private CountComponent deepLinkCountComponent;

    public List<DeepLink> getDeepLinks(SummaryDeepLinkParams summaryDeepLinkParams) {
        String start_date = summaryDeepLinkParams.startDate;
        String end_date = summaryDeepLinkParams.endDate;
        List<DateDuration> dateDurations = Util.getBetweenMonths(start_date, end_date);
        List<DeepLink> deepLinks = new ArrayList<DeepLink>();

        for (DateDuration dd : dateDurations) {
            deepLinks.addAll(deepLinkDao.getDeepLinks(summaryDeepLinkParams.appid, dd.getMin_date(), dd.getMax_date(),
                    summaryDeepLinkParams.feature, summaryDeepLinkParams.campaign, summaryDeepLinkParams.stage,
                    summaryDeepLinkParams.channel, summaryDeepLinkParams.tags, summaryDeepLinkParams.unique));
        }
        return deepLinks;
    }

    public String getDeepLinksWithCount(SummaryDeepLinkParams summaryDeepLinkParams) {
        List<DeepLink> deepLinks = getDeepLinks(summaryDeepLinkParams);
        Map<Long, DeepLinkCount> deepLinkCountMap = new HashMap<>();
        int deepLinkNum = deepLinks.size();
        int startIndex = summaryDeepLinkParams.skipNumber;
        int endIndex = (summaryDeepLinkParams.returnNumber + startIndex) > deepLinkNum ? deepLinkNum: (summaryDeepLinkParams.returnNumber + startIndex);
        long[] tmpIds = new long[summaryDeepLinkParams.returnNumber];
        if(deepLinks.size() <= startIndex) {
            return null;
        }
        for(int i = startIndex; i < endIndex; i++) {
            tmpIds[i] = deepLinks.get(i).getDeeplinkId();
        }
        Map<Long, Map<String, Integer>> counts = deepLinkCountComponent.getsAll(tmpIds);
        Map<Long, DeepLinkCount> dplMap = new HashMap<>(tmpIds.length);
        for (int j = 0; j < tmpIds.length; j++) {
            DeepLinkCount deepLinkCount = new DeepLinkCount(tmpIds[j]);
            Map<String, Integer> countMap = counts.get(tmpIds);
            setDeepLinkCount(deepLinkCount, countMap);
            dplMap.put(tmpIds[j], deepLinkCount);
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("total_count", deepLinkNum);
        JSONArray deepLinkJsonArr = new JSONArray();

        //遍历deepLinks,对应给count赋值(只遍历startIndex和endIndex之间的元素)
        List<DeepLink> resultDeepLinks = new ArrayList<>(deepLinkNum);
        for(int i = startIndex; i < endIndex; i++) {
            deepLinks.get(i).setDeepLinkCount(dplMap.get(deepLinks.get(i).getDeeplinkId()));
            deepLinkJsonArr.add(deepLinks.get(i).toJsonObject());
        }
        resultJson.put("ret", deepLinkJsonArr);
        return resultJson.toString();
    }

    public Map<Long, DeepLinkCount> getDeepLinkSummary(SummaryDeepLinkParams summaryDeepLinkParams) {
        List<DeepLink> deepLinks = getDeepLinks(summaryDeepLinkParams);
        Map<Long, DeepLinkCount> deepLinkCountMap = new HashMap<>();
        long[] tmpIds = new long[50];
        for (int i = 0; i < deepLinks.size(); i++) {
            tmpIds[(i % 50)] = deepLinks.get(i).getDeeplinkId();
            if ((i + 1) % 50 == 0) {
                Map<Long, Map<String, Integer>> counts = deepLinkCountComponent.getsAll(tmpIds);
                // 遍历counts,转成Map<Long, DeepLinkCount>
                Map<Long, DeepLinkCount> dplMap = new HashMap<>(50);
                for (int j = 0; j < 50; j++) {
                    DeepLinkCount deepLinkCount = new DeepLinkCount(tmpIds[j]);
                    Map<String, Integer> countMap = counts.get(tmpIds);
                    setDeepLinkCount(deepLinkCount, countMap);
                    dplMap.put(tmpIds[j], deepLinkCount);
                }
                deepLinkCountMap.putAll(dplMap);
                tmpIds = new long[50];
            }
        }

        if (tmpIds.length > 0) {
            Map<Long, Map<String, Integer>> counts = deepLinkCountComponent.getsAll(tmpIds);
            // 遍历counts,转成Map<Long, DeepLinkCount>
            Map<Long, DeepLinkCount> dplMap = new HashMap<>(tmpIds.length);
            for (int j = 0; j < tmpIds.length; j++) {
                DeepLinkCount deepLinkCount = new DeepLinkCount(tmpIds[j]);
                Map<String, Integer> countMap = counts.get(tmpIds);
                setDeepLinkCount(deepLinkCount, countMap);
                dplMap.put(tmpIds[j], deepLinkCount);
            }
            deepLinkCountMap.putAll(dplMap);
        }

        return deepLinkCountMap;
    }

    private void setDeepLinkCount(DeepLinkCount dplc, Map<String, Integer> dplCountMap) {
        dplc.setIos_click(dplCountMap.get(DeepLinkCount.CountType.ios_click));
        dplc.setIos_install(dplCountMap.get(DeepLinkCount.CountType.ios_click.toString()));
        dplc.setIos_open(dplCountMap.get(DeepLinkCount.CountType.ios_open.toString()));
        dplc.setAdr_click(dplCountMap.get(DeepLinkCount.CountType.adr_click.toString()));
        dplc.setAdr_install(dplCountMap.get(DeepLinkCount.CountType.adr_install.toString()));
        dplc.setAdr_open(dplCountMap.get(DeepLinkCount.CountType.adr_open.toString()));

        dplc.setPc_click(dplCountMap.get(DeepLinkCount.CountType.pc_click));
        dplc.setPc_ios_scan(dplCountMap.get(DeepLinkCount.CountType.pc_ios_scan));
        dplc.setPc_ios_install(dplCountMap.get(DeepLinkCount.CountType.pc_ios_install));
        dplc.setPc_ios_open(dplCountMap.get(DeepLinkCount.CountType.pc_ios_open));
        dplc.setPc_adr_scan(dplCountMap.get(DeepLinkCount.CountType.pc_adr_scan));
        dplc.setPc_adr_install(dplCountMap.get(DeepLinkCount.CountType.pc_adr_install));
        dplc.setPc_adr_open(dplCountMap.get(DeepLinkCount.CountType.pc_adr_open));
    }
}
