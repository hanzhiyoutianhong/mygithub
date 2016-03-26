package cc.linkedme.service.webapi;

import cc.linkedme.commons.counter.component.CountComponent;
import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.DeepLinkCount;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
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
        List<String> months = Util.getBetweenMonths(start_date, end_date);
        List<DeepLink> deepLinks = new ArrayList<DeepLink>();
        deepLinks.addAll(deepLinkDao.getDeepLinks(summaryDeepLinkParams.appid, summaryDeepLinkParams.startDate,
                summaryDeepLinkParams.endDate, summaryDeepLinkParams.feature, summaryDeepLinkParams.campaign, summaryDeepLinkParams.stage,
                summaryDeepLinkParams.channel, summaryDeepLinkParams.tags, summaryDeepLinkParams.unique));
        for (String month : months) {
            deepLinks.addAll(deepLinkDao.getDeepLinks(summaryDeepLinkParams.appid, summaryDeepLinkParams.startDate, null,
                    summaryDeepLinkParams.feature, summaryDeepLinkParams.campaign, summaryDeepLinkParams.stage,
                    summaryDeepLinkParams.channel, summaryDeepLinkParams.tags, summaryDeepLinkParams.unique));
        }
        deepLinks.addAll(deepLinkDao.getDeepLinks(summaryDeepLinkParams.appid, null, summaryDeepLinkParams.endDate,
                summaryDeepLinkParams.feature, summaryDeepLinkParams.campaign, summaryDeepLinkParams.stage, summaryDeepLinkParams.channel,
                summaryDeepLinkParams.tags, summaryDeepLinkParams.unique));
        return deepLinks;
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
                }
                deepLinkCountMap.putAll(dplMap);
            }
        }
        return deepLinkCountMap;
    }

    private void setDeepLinkCount(DeepLinkCount dplc, Map<String, Integer> dplCountMap) {
        dplc.setWx_ios_install(dplCountMap.get(DeepLinkCount.CountType.wx_ios_install));
        dplc.setWx_ios_open(dplCountMap.get(DeepLinkCount.CountType.wx_ios_open.toString()));
        dplc.setWx_ios_click(dplCountMap.get(DeepLinkCount.CountType.wx_ios_click.toString()));
        dplc.setWx_adr_click(dplCountMap.get(DeepLinkCount.CountType.wx_adr_click.toString()));
        dplc.setWx_adr_install(dplCountMap.get(DeepLinkCount.CountType.wx_adr_install.toString()));
        dplc.setWx_adr_open(dplCountMap.get(DeepLinkCount.CountType.wx_adr_open.toString()));

        dplc.setWb_ios_install(dplCountMap.get(DeepLinkCount.CountType.wb_ios_install));
        dplc.setWb_ios_open(dplCountMap.get(DeepLinkCount.CountType.wb_ios_open.toString()));
        dplc.setWb_ios_click(dplCountMap.get(DeepLinkCount.CountType.wb_ios_click.toString()));
        dplc.setWb_adr_click(dplCountMap.get(DeepLinkCount.CountType.wb_adr_click.toString()));
        dplc.setWb_adr_install(dplCountMap.get(DeepLinkCount.CountType.wb_adr_install.toString()));
        dplc.setWb_adr_open(dplCountMap.get(DeepLinkCount.CountType.wb_adr_open.toString()));

        dplc.setPc_click(dplCountMap.get(DeepLinkCount.CountType.pc_click));
        dplc.setPc_ios_scan(dplCountMap.get(DeepLinkCount.CountType.pc_ios_scan));
        dplc.setPc_ios_install(dplCountMap.get(DeepLinkCount.CountType.pc_ios_install));
        dplc.setPc_ios_open(dplCountMap.get(DeepLinkCount.CountType.pc_ios_open));
        dplc.setPc_adr_scan(dplCountMap.get(DeepLinkCount.CountType.pc_adr_scan));
        dplc.setPc_adr_install(dplCountMap.get(DeepLinkCount.CountType.pc_adr_install));
        dplc.setPc_adr_open(dplCountMap.get(DeepLinkCount.CountType.pc_adr_open));
    }
}
