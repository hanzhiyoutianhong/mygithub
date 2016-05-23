package cc.linkedme.service.webapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.api.client.repackaged.com.google.common.base.Strings;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPipelineReadCallback;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.redis.JedisReadPipeline;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.dao.webapi.BtnCountDao;
import cc.linkedme.dao.webapi.DeepLinkDateCountDao;
import cc.linkedme.data.dao.util.DateDuration;
import cc.linkedme.data.model.ButtonCount;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.DeepLinkCount;
import cc.linkedme.data.model.DeepLinkDateCount;
import cc.linkedme.data.model.params.SummaryButtonParams;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by LinkedME01 on 16/3/20.
 */

@Service
public class SummaryService {

    @Resource
    DeepLinkDao deepLinkDao;

    @Resource
    DeepLinkDateCountDao deepLinkDateCountDao;

    @Resource
    BtnCountDao btnCountDao;

    @Resource
    private ShardingSupportHash<JedisPort> deepLinkCountShardingSupport;

    @Resource
    private BtnService btnService;

    @Resource
    ShardingSupportHash<JedisPort> btnCountShardingSupport;

    public Comparator comparator = new Comparator<JSONArray>() {
        @Override
        public int compare(JSONArray o1, JSONArray o2) {
            long date1 = o1.getLong(0);
            long date2 = o2.getLong(0);

            return date1 > date2 ? 1 : (date1 == date2 ? 0 : -1);
        }
    };

    public String getDeepLinksCounts(SummaryDeepLinkParams summaryDeepLinkParams) {
        List<DeepLinkDateCount> deepLinkDateCountList = deepLinkDateCountDao.getDeepLinksDateCounts(summaryDeepLinkParams.appid,
                summaryDeepLinkParams.startDate, summaryDeepLinkParams.endDate);
        long iosClick = 0, iosOpen = 0, iosInstall = 0, adrClick = 0, adrOpen = 0, adrInstall = 0;
        long pcClick = 0, pcIosScan = 0, pcAdrScan = 0, pcIosOpen = 0, pcAdrOpen = 0, pcIosInstall = 0, pcAdrInstall = 0;
        Map<String, Map<String, Long>> allDateCounts = new HashMap<>();
        Set<Long> deepLinkIdSet = new HashSet<>();
        DeepLink dl;
        int link_count = 0;
        for (DeepLinkDateCount deepLinkDateCount : deepLinkDateCountList) {
            if (!deepLinkIdSet.contains(deepLinkDateCount.getDeeplinkId())) {
                deepLinkIdSet.add(deepLinkDateCount.getDeeplinkId());
                dl = deepLinkDao.getDeepLinkInfo(deepLinkDateCount.getDeeplinkId(), summaryDeepLinkParams.appid);

                if (!isValidDeepLink(summaryDeepLinkParams, dl)) {
                    continue;
                }
            }

            link_count++;

            putElementToAllDateCounts(allDateCounts, deepLinkDateCount);

            iosClick += deepLinkDateCount.getIosClick();
            iosOpen += deepLinkDateCount.getIosOpen();
            iosInstall += deepLinkDateCount.getIosInstall();

            adrClick += deepLinkDateCount.getAdrClick();
            adrOpen += deepLinkDateCount.getAdrOpen();
            adrInstall += deepLinkDateCount.getAdrInstall();

            pcClick += deepLinkDateCount.getPcClick();
            pcIosScan += deepLinkDateCount.getPcIosScan();
            pcAdrScan += deepLinkDateCount.getPcAdrScan();
            pcIosOpen += deepLinkDateCount.getIosOpen();
            pcAdrOpen += deepLinkDateCount.getAdrOpen();
            pcIosInstall += deepLinkDateCount.getPcIosInstall();
            pcAdrInstall += deepLinkDateCount.getPcAdrInstall();

        }

        JSONObject resultJson = getDeepLinkCountJson(allDateCounts, iosClick, iosOpen, iosInstall, adrClick, adrOpen, adrInstall, pcClick,
                pcIosScan, pcIosOpen, pcIosInstall, pcAdrScan, pcAdrOpen, pcAdrInstall, link_count);

        return resultJson.toString();
    }

    public String getDeepLinkHistoryCountNew(SummaryDeepLinkParams summaryDeepLinkParams) {
        List<DeepLinkDateCount> deepLinkDateCountList = deepLinkDateCountDao.getDeepLinkDateCount(summaryDeepLinkParams.appid,
                summaryDeepLinkParams.deepLinkId, summaryDeepLinkParams.startDate, summaryDeepLinkParams.endDate);
        long iosClick = 0, iosOpen = 0, iosInstall = 0, adrClick = 0, adrOpen = 0, adrInstall = 0;
        long pcClick = 0, pcIosScan = 0, pcAdrScan = 0, pcIosOpen = 0, pcAdrOpen = 0, pcIosInstall = 0, pcAdrInstall = 0;
        Map<String, Map<String, Long>> allDateCounts = new HashMap<>();
        int link_count = 0;
        for (DeepLinkDateCount deepLinkDateCount : deepLinkDateCountList) {
            putElementToAllDateCounts(allDateCounts, deepLinkDateCount);

            iosClick += deepLinkDateCount.getIosClick();
            iosOpen += deepLinkDateCount.getIosOpen();
            iosInstall += deepLinkDateCount.getIosInstall();

            adrClick += deepLinkDateCount.getAdrClick();
            adrOpen += deepLinkDateCount.getAdrOpen();
            adrInstall += deepLinkDateCount.getAdrInstall();

            pcClick += deepLinkDateCount.getPcClick();
            pcIosScan += deepLinkDateCount.getPcIosScan();
            pcAdrScan += deepLinkDateCount.getPcAdrScan();
            pcIosOpen += deepLinkDateCount.getIosOpen();
            pcAdrOpen += deepLinkDateCount.getAdrOpen();
            pcIosInstall += deepLinkDateCount.getPcIosInstall();
            pcAdrInstall += deepLinkDateCount.getPcAdrInstall();

        }

        JSONObject resultJson = getDeepLinkCountJson(allDateCounts, iosClick, iosOpen, iosInstall, adrClick, adrOpen, adrInstall, pcClick,
                pcIosScan, pcIosOpen, pcIosInstall, pcAdrScan, pcAdrOpen, pcAdrInstall, link_count);

        return resultJson.toString();
    }

    public String getDeepLinksHistoryCounts(SummaryDeepLinkParams summaryDeepLinkParams, long[] deepLinkIds) {
        long iosClick = 0, iosOpen = 0, iosInstall = 0, adrClick = 0, adrOpen = 0, adrInstall = 0;
        long pcClick = 0, pcIosScan = 0, pcAdrScan = 0, pcIosOpen = 0, pcAdrOpen = 0, pcIosInstall = 0, pcAdrInstall = 0;
        for (long deepLinkId : deepLinkIds) {
            List<DeepLinkDateCount> deepLinkDateCountList = deepLinkDateCountDao.getDeepLinkDateCount(summaryDeepLinkParams.appid,
                    deepLinkId, summaryDeepLinkParams.startDate, summaryDeepLinkParams.endDate);
            for (DeepLinkDateCount deepLinkDateCount : deepLinkDateCountList) {
                iosClick += deepLinkDateCount.getIosClick();
                iosOpen += deepLinkDateCount.getIosOpen();
                iosInstall += deepLinkDateCount.getIosInstall();

                adrClick += deepLinkDateCount.getAdrClick();
                adrOpen += deepLinkDateCount.getAdrOpen();
                adrInstall += deepLinkDateCount.getAdrInstall();

                pcClick += deepLinkDateCount.getPcClick();
                pcIosScan += deepLinkDateCount.getPcIosScan();
                pcAdrScan += deepLinkDateCount.getPcAdrScan();
                pcIosOpen += deepLinkDateCount.getIosOpen();
                pcAdrOpen += deepLinkDateCount.getAdrOpen();
                pcIosInstall += deepLinkDateCount.getPcIosInstall();
                pcAdrInstall += deepLinkDateCount.getPcAdrInstall();
            }
        }

        JSONObject iosJson = new JSONObject();
        iosJson.put("click", iosClick);
        iosJson.put("open", iosOpen);
        iosJson.put("install", iosInstall);

        JSONObject adrJson = new JSONObject();
        adrJson.put("click", adrClick);
        adrJson.put("open", adrOpen);
        adrJson.put("install", adrInstall);

        JSONObject pcJson = new JSONObject();
        pcJson.put("click", pcClick);
        pcJson.put("pc_ios_scan", pcIosScan);
        pcJson.put("pc_ios_open", pcIosOpen);
        pcJson.put("pc_ios_install", pcIosInstall);
        pcJson.put("pc_adr_scan", pcAdrScan);
        pcJson.put("pc_adr_open", pcAdrOpen);
        pcJson.put("pc_adr_install", pcAdrInstall);

        JSONObject resultJson = new JSONObject();
        resultJson.put("ios", iosJson);
        resultJson.put("android", adrJson);
        resultJson.put("pc", pcJson);
        resultJson.put("link_count", deepLinkIds.length);

        return resultJson.toString();
    }

    private void putElementToAllDateCounts(Map<String, Map<String, Long>> allDateCounts, DeepLinkDateCount deepLinkDateCount) {
        Map<String, Long> count = allDateCounts.get(deepLinkDateCount.getDate());
        if (count != null) {
            count.put("click", count.get("click") + deepLinkDateCount.getClick());
            count.put("open", count.get("open") + deepLinkDateCount.getOpen());
            count.put("install", count.get("install") + deepLinkDateCount.getInstall());
        } else {
            count = new HashMap<>();
            count.put("click", deepLinkDateCount.getClick());
            count.put("open", deepLinkDateCount.getOpen());
            count.put("install", deepLinkDateCount.getInstall());
            allDateCounts.put(deepLinkDateCount.getDate(), count);
        }
    }

    private JSONObject getDeepLinkCountJson(Map<String, Map<String, Long>> allDateCounts, long iosClick, long iosOpen, long iosInstall,
            long adrClick, long adrOpen, long adrInstall, long pcClick, long pcIosScan, long pcIosOpen, long pcIosInstall, long pcAdrScan,
            long pcAdrOpen, long pcAdrInstall, int link_count) {

        JSONArray clickArr = new JSONArray();
        JSONArray openArr = new JSONArray();
        JSONArray installArr = new JSONArray();

        int i = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<String, Map<String, Long>> entry : allDateCounts.entrySet()) {
            String date = entry.getKey();
            Map<String, Long> countMap = entry.getValue();
            long timeStamp = strDateToTimestamps(date, simpleDateFormat);
            JSONArray clickJson = new JSONArray();
            clickJson.add(0, timeStamp);
            clickJson.add(1, countMap.get("click"));
            clickArr.add(i, clickJson);

            JSONArray openJson = new JSONArray();
            openJson.add(0, timeStamp);
            openJson.add(1, countMap.get("open"));
            openArr.add(i, openJson);

            JSONArray installJson = new JSONArray();
            installJson.add(0, timeStamp);
            installJson.add(1, countMap.get("install"));
            installArr.add(i, installJson);

            i++;
        }

        clickArr.sort(comparator);
        openArr.sort(comparator);
        installArr.sort(comparator);

        JSONObject iosJson = new JSONObject();
        iosJson.put("click", iosClick);
        iosJson.put("open", iosOpen);
        iosJson.put("install", iosInstall);

        JSONObject adrJson = new JSONObject();
        adrJson.put("click", adrClick);
        adrJson.put("open", adrOpen);
        adrJson.put("install", adrInstall);

        JSONObject pcJson = new JSONObject();
        pcJson.put("click", pcClick);
        pcJson.put("pc_ios_scan", pcIosScan);
        pcJson.put("pc_ios_open", pcIosOpen);
        pcJson.put("pc_ios_install", pcIosInstall);
        pcJson.put("pc_adr_scan", pcAdrScan);
        pcJson.put("pc_adr_open", pcAdrOpen);
        pcJson.put("pc_adr_install", pcAdrInstall);

        JSONObject resultJson = new JSONObject();
        resultJson.put("click", clickArr);
        resultJson.put("open", openArr);
        resultJson.put("install", installArr);
        resultJson.put("link_count", link_count);
        resultJson.put("ios", iosJson);
        resultJson.put("android", adrJson);
        resultJson.put("pc", pcJson);
        return resultJson;
    }

    private boolean isValidDeepLink(SummaryDeepLinkParams summaryDeepLinkParams, DeepLink deepLink) {
        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;
        boolean e = false;

        if ((deepLink.getFeature() == null && Strings.isNullOrEmpty(summaryDeepLinkParams.feature))
                || (deepLink.getFeature() != null && deepLink.getFeature().contains(summaryDeepLinkParams.feature))) {
            a = true;
        }
        if ((deepLink.getCampaign() == null && Strings.isNullOrEmpty(summaryDeepLinkParams.campaign))
                || (deepLink.getCampaign() != null && deepLink.getCampaign().contains(summaryDeepLinkParams.campaign))) {
            b = true;
        }
        if ((deepLink.getStage() == null && Strings.isNullOrEmpty(summaryDeepLinkParams.stage))
                || (deepLink.getStage() != null && deepLink.getStage().contains(summaryDeepLinkParams.stage))) {
            c = true;
        }
        if ((deepLink.getTags() == null && Strings.isNullOrEmpty(summaryDeepLinkParams.tags))
                || (deepLink.getTags() != null && deepLink.getTags().contains(summaryDeepLinkParams.tags))) {
            c = true;
        }
        if ((deepLink.getSource() == null && Strings.isNullOrEmpty(summaryDeepLinkParams.source))
                || (deepLink.getSource() != null && deepLink.getCampaign().equals(summaryDeepLinkParams.campaign))) {
            e = true;
        }
        return a && b && c && d && e;
    }

    public List<DeepLink> getDeepLinks(SummaryDeepLinkParams summaryDeepLinkParams) {
        String start_date = summaryDeepLinkParams.startDate;
        String end_date = summaryDeepLinkParams.endDate;

        String onlineTime = "2016-05-01";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date onlineDate = simpleDateFormat.parse(onlineTime);
            Date stDate = simpleDateFormat.parse(start_date);
            Date endDate = simpleDateFormat.parse(end_date);
            Date currentDate = new Date();

            if (stDate.after(currentDate) || endDate.before(onlineDate)) {
                throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "date is invalid!");
            } else {
                if (stDate.before(onlineDate)) {
                    start_date = onlineTime;
                }
                if (endDate.after(currentDate)) {
                    end_date = simpleDateFormat.format(currentDate);
                }
            }
        } catch (ParseException e) {
            ApiLogger.warn("SummaryService.getDeepLinks parse date failed", e);
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "date param is illegal");
        }

        List<DateDuration> dateDurations = Util.getBetweenMonths(start_date, end_date);
        List<DeepLink> deepLinks = new ArrayList<>();

        for (DateDuration dd : dateDurations) {
            deepLinks.addAll(deepLinkDao.getDeepLinks(summaryDeepLinkParams.appid, dd.getMin_date(), dd.getMax_date(),
                    summaryDeepLinkParams.feature, summaryDeepLinkParams.campaign, summaryDeepLinkParams.stage,
                    summaryDeepLinkParams.channel, summaryDeepLinkParams.tags, summaryDeepLinkParams.source, summaryDeepLinkParams.unique));
        }
        return deepLinks;
    }

    public String getDeepLinksWithCount(SummaryDeepLinkParams summaryDeepLinkParams) {
        List<DeepLink> deepLinks = getDeepLinks(summaryDeepLinkParams);
        int deepLinkNum = deepLinks.size();
        int startIndex = summaryDeepLinkParams.skipNumber;
        if (startIndex < 0) {
            startIndex = 0;
        }
        int endIndex = (summaryDeepLinkParams.returnNumber + startIndex) > deepLinkNum
                ? deepLinkNum
                : (summaryDeepLinkParams.returnNumber + startIndex);
        long[] tmpIds = new long[endIndex - startIndex];
        if (deepLinks.size() <= startIndex) {
            return null;
        }
        for (int i = startIndex; i < endIndex; i++) {
            tmpIds[i - startIndex] = deepLinks.get(i).getDeeplinkId();
        }

        Map<Long, Map<String, String>> counts = getCounts(tmpIds);

        JSONObject resultJson = new JSONObject();
        resultJson.put("total_count", deepLinkNum);
        JSONArray deepLinkJsonArr = new JSONArray();

        // 遍历deepLinks,对应给count赋值(只遍历startIndex和endIndex之间的元素)
        for (int i = startIndex; i < endIndex; i++) {
            long deepLinkId = deepLinks.get(i).getDeeplinkId();
            DeepLinkCount deepLinkCountObject = new DeepLinkCount(deepLinkId);
            Map<String, String> countMap = counts.get(deepLinkId);
            if (countMap != null && countMap.size() > 0) {
                setDeepLinkCount(deepLinkCountObject, countMap);
            }
            deepLinks.get(i).setDeepLinkCount(deepLinkCountObject);
            deepLinkJsonArr.add(i - startIndex, deepLinks.get(i).toJsonObject());
        }
        resultJson.put("ret", deepLinkJsonArr);
        return resultJson.toString();
    }

    public String getDeepLinkInfoByDeepLinkId(SummaryDeepLinkParams summaryDeepLinkParams) {
        DeepLink deepLinkInfo = deepLinkDao.getDeepLinkInfo(summaryDeepLinkParams.deepLinkId, summaryDeepLinkParams.appid);

        Map<String, String> count = getCount(summaryDeepLinkParams.deepLinkId);

        DeepLinkCount deepLinkCount = new DeepLinkCount(summaryDeepLinkParams.deepLinkId);

        if (count != null) {
            setDeepLinkCount(deepLinkCount, count);
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("deeplink_id", summaryDeepLinkParams.deepLinkId);

        String deeplink_url = Constants.DEEPLINK_HTTPS_PREFIX + "/" + Base62.encode(deepLinkInfo.getAppId()) + "/"
                + Base62.encode(summaryDeepLinkParams.deepLinkId);
        resultJson.put("deeplink_url", deeplink_url);

        int click = deepLinkCount.getAdr_click() + deepLinkCount.getIos_click() + deepLinkCount.getPc_click();
        int open =
                deepLinkCount.getAdr_open() + deepLinkCount.getIos_open() + deepLinkCount.getPc_adr_open() + deepLinkCount.getPc_ios_open();
        int install = deepLinkCount.getAdr_install() + deepLinkCount.getIos_install() + deepLinkCount.getPc_adr_install()
                + deepLinkCount.getPc_ios_install();
        resultJson.put("click", click);
        resultJson.put("open", open);
        resultJson.put("install", install);
        resultJson.put("feature", deepLinkInfo.getFeature());
        resultJson.put("campaign", deepLinkInfo.getCampaign());
        resultJson.put("stage", deepLinkInfo.getStage());
        resultJson.put("channel", deepLinkInfo.getChannel());
        resultJson.put("unique", "0");
        resultJson.put("tag", deepLinkInfo.getTags());
        resultJson.put("creation_time", deepLinkInfo.getCreateTime());
        resultJson.put("creation_type", deepLinkInfo.getSource());

        JSONObject retJson = getCountJson(deepLinkCount.getIos_click(), deepLinkCount.getIos_open(), deepLinkCount.getIos_install(),
                deepLinkCount.getAdr_click(), deepLinkCount.getAdr_open(), deepLinkCount.getAdr_install(), deepLinkCount.getPc_click(),
                deepLinkCount.getPc_ios_scan(), deepLinkCount.getPc_ios_open(), deepLinkCount.getPc_ios_install(),
                deepLinkCount.getPc_adr_scan(), deepLinkCount.getPc_adr_open(), deepLinkCount.getPc_adr_install());

        resultJson.put("data", retJson);

        return resultJson.toString();
    }

    public String getDeepLinkHistoryCount(SummaryDeepLinkParams summaryDeepLinkParams) {
        List<DeepLinkDateCount> deepLinkDateCounts = deepLinkDateCountDao.getDeepLinkDateCount(summaryDeepLinkParams.appid,
                summaryDeepLinkParams.deepLinkId, summaryDeepLinkParams.startDate, summaryDeepLinkParams.endDate);

        JSONArray clickArr = new JSONArray();
        JSONArray openArr = new JSONArray();
        JSONArray installArr = new JSONArray();

        int i = 0;
        for (DeepLinkDateCount deepLinkDateCount : deepLinkDateCounts) {
            String date = deepLinkDateCount.getDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long timeStamp = strDateToTimestamps(date, simpleDateFormat);
            JSONArray clickJson = new JSONArray();
            clickJson.add(0, timeStamp);
            clickJson.add(1, deepLinkDateCount.getClick());
            clickArr.add(i, clickJson);

            JSONArray openJson = new JSONArray();
            openJson.add(0, timeStamp);
            openJson.add(1, deepLinkDateCount.getOpen());
            openArr.add(i, openJson);

            JSONArray installJson = new JSONArray();
            installJson.add(0, timeStamp);
            installJson.add(1, deepLinkDateCount.getInstall());
            installArr.add(i, installJson);

            i++;
        }

        clickArr.sort(comparator);
        openArr.sort(comparator);
        installArr.sort(comparator);

        JSONObject resultJson = new JSONObject();
        resultJson.put("click", clickArr);
        resultJson.put("open", openArr);
        resultJson.put("install", installArr);
        return resultJson.toString();
    }

    public String getDeepLinksHistoryCount(SummaryDeepLinkParams summaryDeepLinkParams) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = summaryDeepLinkParams.startDate;
        String endDate = summaryDeepLinkParams.endDate;
        summaryDeepLinkParams.startDate = "2016-05-01";
        summaryDeepLinkParams.endDate = simpleDateFormat.format(new Date());

        List<DeepLink> deepLinks = getDeepLinks(summaryDeepLinkParams);// 查询所有短链

        Map<String, Map<String, Long>> allCounts = new HashMap<>();

        for (int i = 0; i < deepLinks.size(); i++) { // TODO 改成批量查询
            List<DeepLinkDateCount> deepLinkDateCounts = deepLinkDateCountDao.getDeepLinkDateCount(summaryDeepLinkParams.appid,
                    deepLinks.get(i).getDeeplinkId(), startDate, endDate);
            for (DeepLinkDateCount deepLinkDateCount : deepLinkDateCounts) {
                putElementToAllDateCounts(allCounts, deepLinkDateCount);
            }
        }

        JSONArray clickArr = new JSONArray();
        JSONArray openArr = new JSONArray();
        JSONArray installArr = new JSONArray();

        int i = 0;
        for (Map.Entry<String, Map<String, Long>> entry : allCounts.entrySet()) {
            String date = entry.getKey();
            Map<String, Long> countMap = entry.getValue();
            long timeStamp = strDateToTimestamps(date, simpleDateFormat);
            JSONArray clickJson = new JSONArray();
            clickJson.add(0, timeStamp);
            clickJson.add(1, countMap.get("click"));
            clickArr.add(i, clickJson);

            JSONArray openJson = new JSONArray();
            openJson.add(0, timeStamp);
            openJson.add(1, countMap.get("open"));
            openArr.add(i, openJson);

            JSONArray installJson = new JSONArray();
            installJson.add(0, timeStamp);
            installJson.add(1, countMap.get("install"));
            installArr.add(i, installJson);

            i++;
        }

        clickArr.sort(comparator);
        openArr.sort(comparator);
        installArr.sort(comparator);

        JSONObject resultJson = new JSONObject();
        resultJson.put("click", clickArr);
        resultJson.put("open", openArr);
        resultJson.put("install", installArr);
        return resultJson.toString();
    }

    public JSONObject getCountJson(int iosClick, int iosOpen, int iosInstall, int adrClick, int adrOpen, int adrInstall, int pcClick,
            int pcIosScan, int pcIosOpen, int pcIosInstall, int pcAdrScan, int pcAdrOpen, int pcAdrInstall) {
        JSONObject iosJson = new JSONObject();
        iosJson.put("click", iosClick);
        iosJson.put("open", iosOpen);
        iosJson.put("install", iosInstall);

        JSONObject adrJson = new JSONObject();
        adrJson.put("click", adrClick);
        adrJson.put("open", adrOpen);
        adrJson.put("install", adrInstall);

        JSONObject pcJson = new JSONObject();
        pcJson.put("click", pcClick);
        pcJson.put("pc_ios_scan", pcIosScan);
        pcJson.put("pc_ios_open", pcIosOpen);
        pcJson.put("pc_ios_install", pcIosInstall);
        pcJson.put("pc_adr_scan", pcAdrScan);
        pcJson.put("pc_adr_open", pcAdrOpen);
        pcJson.put("pc_adr_install", pcAdrInstall);

        JSONObject retJson = new JSONObject();
        retJson.put("ios", iosJson);
        retJson.put("android", adrJson);
        retJson.put("pc", pcJson);
        return retJson;
    }

    public DeepLinkCount getDeepLinkCounts(long deepLinkId) {
        Map<String, String> count = getCount(deepLinkId);

        DeepLinkCount deepLinkCount = new DeepLinkCount(deepLinkId);

        if (count != null) {
            setDeepLinkCount(deepLinkCount, count);
        }

        return deepLinkCount;
    }

    public Map<Long, DeepLinkCount> getDeepLinkSummary(SummaryDeepLinkParams summaryDeepLinkParams) {
        List<DeepLink> deepLinks = getDeepLinks(summaryDeepLinkParams);
        Map<Long, DeepLinkCount> deepLinkCountMap = new HashMap<>();
        long[] tmpIds = new long[50];
        for (int i = 0; i < deepLinks.size(); i++) {
            tmpIds[(i % 50)] = deepLinks.get(i).getDeeplinkId();
            if ((i + 1) % 50 == 0) {
                Map<Long, Map<String, String>> counts = getCounts(tmpIds);
                // 遍历counts,转成Map<Long, DeepLinkCount>
                if (counts != null && counts.size() > 0) {
                    Map<Long, DeepLinkCount> dplMap = new HashMap<>();
                    for (int j = 0; j < 50; j++) {
                        DeepLinkCount deepLinkCount = new DeepLinkCount(tmpIds[j]);
                        Map<String, String> countMap = counts.get(tmpIds);
                        if (countMap != null) {
                            setDeepLinkCount(deepLinkCount, countMap);
                        }
                        dplMap.put(tmpIds[j], deepLinkCount);
                    }
                    deepLinkCountMap.putAll(dplMap);
                }
                tmpIds = new long[50];
            }
        } // 只查返回有count的deeplink

        if (deepLinks.size() % 50 != 0) {
            Map<Long, Map<String, String>> counts = getCounts(tmpIds);
            // 遍历counts,转成Map<Long, DeepLinkCount>
            if (counts != null && counts.size() > 0) {
                Map<Long, DeepLinkCount> dplMap = new HashMap<>();
                for (int j = 0; j < tmpIds.length; j++) {
                    if (tmpIds[j] == 0) {
                        continue;
                    }
                    DeepLinkCount deepLinkCount = new DeepLinkCount(tmpIds[j]);
                    Map<String, String> countMap = counts.get(tmpIds[j]);
                    if (countMap != null) {
                        setDeepLinkCount(deepLinkCount, countMap);
                    }
                    dplMap.put(tmpIds[j], deepLinkCount);
                }
                deepLinkCountMap.putAll(dplMap);
            }
        }

        return deepLinkCountMap;
    }

    public void setDeepLinkCount(DeepLinkCount dplc, Map<String, String> dplCountMap) {
        int iosClick = 0, iosInstall = 0, iosOpen = 0, adrClick = 0;
        if (dplCountMap.get(DeepLinkCount.CountType.ios_click.toString()) != null) {
            iosClick = Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.ios_click.toString()));
            dplc.setIos_click(iosClick);
        }
        if (dplCountMap.get(DeepLinkCount.CountType.ios_install.toString()) != null) {
            iosInstall = Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.ios_install.toString()));
            dplc.setIos_install(iosInstall);
        }
        if (dplCountMap.get(DeepLinkCount.CountType.ios_open.toString()) != null) {
            iosOpen = Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.ios_open.toString()));
            dplc.setIos_open(iosOpen);
        }

        if (dplCountMap.get(DeepLinkCount.CountType.adr_click.toString()) != null) {
            adrClick = Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.adr_click.toString()));
            dplc.setAdr_click(adrClick);
        }

        if (dplCountMap.get(DeepLinkCount.CountType.adr_install.toString()) != null) {
            dplc.setAdr_install(Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.adr_install.toString())));
        }

        if (dplCountMap.get(DeepLinkCount.CountType.adr_open.toString()) != null) {
            dplc.setAdr_open(Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.adr_open.toString())));
        }

        if (dplCountMap.get(DeepLinkCount.CountType.pc_click.toString()) != null) {
            dplc.setPc_click(Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.pc_click.toString())));
        }
        if (dplCountMap.get(DeepLinkCount.CountType.pc_ios_scan.toString()) != null) {
            dplc.setPc_ios_scan(Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.pc_ios_scan.toString())));
        }
        if (dplCountMap.get(DeepLinkCount.CountType.pc_adr_scan.toString()) != null) {
            dplc.setPc_adr_scan(Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.pc_adr_scan.toString())));
        }
        if (dplCountMap.get(DeepLinkCount.CountType.pc_ios_open.toString()) != null) {
            dplc.setPc_ios_open(Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.pc_ios_open.toString())));
        }
        if (dplCountMap.get(DeepLinkCount.CountType.pc_adr_open.toString()) != null) {
            dplc.setPc_adr_open(Integer.parseInt(dplCountMap.get(DeepLinkCount.CountType.pc_adr_open.toString())));
        }
        // dplc.setPc_ios_install(dplCountMap.get(DeepLinkCount.CountType.pc_ios_install));
        // dplc.setPc_adr_install(dplCountMap.get(DeepLinkCount.CountType.pc_adr_install));
    }

    public String getButtonsIncome(SummaryButtonParams summaryButtonParams) {
        List<ButtonInfo> buttonInfos = btnService.getAllButtonsByAppId(summaryButtonParams.app_id);
        if (buttonInfos.size() == 0) {
            return "[]";
        }
        List<CountRank> countRanks = new ArrayList<>(buttonInfos.size());
        // TODO 不要循环从redis里取数据,后续改成批量获取
        for (ButtonInfo buttonInfo : buttonInfos) {
            String hashKey = String.valueOf(buttonInfo.getAppId()) + buttonInfo.getConsumerAppId();
            JedisPort redisClient = btnCountShardingSupport.getClient(hashKey);
            String income = redisClient.get(hashKey + ".income");
            CountRank countRank = new CountRank();
            countRank.app_id = buttonInfo.getAppId();
            countRank.app_name = buttonInfo.getConsumerAppInfo().getAppName();
            countRank.income = (income == null ? 0 : Float.parseFloat(income));
            countRanks.add(countRank);
        }
        Collections.sort(countRanks);
        int resultCount = summaryButtonParams.return_number < countRanks.size() ? summaryButtonParams.return_number : countRanks.size();
        List<String> countRankJson = new ArrayList<>(countRanks.size());
        for (int i = 0; i < resultCount; i++) {
            countRanks.get(i).rank = i;
            countRankJson.add(countRanks.get(i).toJson());
        }
        return new StringBuilder().append("[").append(StringUtils.join(countRankJson, ",")).append("]").toString();
    }

    public String getHistoryIncome(SummaryButtonParams summaryButtonParams) {
        List<ButtonInfo> buttonInfos = btnService.getAllButtonsByAppId(summaryButtonParams.app_id);
        Set<String> consumerAppids = new HashSet<>();
        JSONArray resultJsonArray = new JSONArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (ButtonInfo buttonInfo : buttonInfos) {
            if (consumerAppids.contains(buttonInfo.getConsumerAppId())) {
                continue;
            }
            List<ButtonCount> buttonCounts = btnCountDao.getConsumerIncome(summaryButtonParams.app_id, buttonInfo.getConsumerAppId(),
                    summaryButtonParams.start_date, summaryButtonParams.end_date);
            Map<String, Float> resultCountMap = new HashMap<>();
            // 按天汇总consumer_app的金额
            for (ButtonCount buttonCount : buttonCounts) {
                Float income = resultCountMap.get(buttonCount.getDate());
                if (income == null) {
                    resultCountMap.put(buttonCount.getDate(), buttonCount.getIncome());
                } else {
                    resultCountMap.put(buttonCount.getDate(), income + buttonCount.getIncome());
                }
            }

            // 变成json格式
            JSONObject consumerIncomeJson = new JSONObject();
            consumerIncomeJson.put("app_id", buttonInfo.getConsumerAppId());
            consumerIncomeJson.put("app_name", buttonInfo.getConsumerAppInfo().getAppName());
            JSONArray dataJsonArray = new JSONArray();

            for (Map.Entry<String, Float> entry : resultCountMap.entrySet()) {
                JSONArray dateJson = new JSONArray();
                dateJson.add(0, strDateToTimestamps(entry.getKey(), simpleDateFormat));
                dateJson.add(1, entry.getValue());
                dataJsonArray.add(dateJson); // TODO 要有顺序
            }
            consumerIncomeJson.put("data", dataJsonArray);
            resultJsonArray.add(consumerIncomeJson);
        }

        return resultJsonArray.toString();
    }

    public String getBtnHistoryCounts(SummaryButtonParams summaryButtonParams) {
        List<ButtonCount> buttonCounts = btnCountDao.getButtonCounts(summaryButtonParams.app_id, summaryButtonParams.btn_id,
                summaryButtonParams.start_date, summaryButtonParams.end_date);
        Map<String, Map<String, Float>> resultCountMap = new HashMap<>();
        for (ButtonCount buttonCount : buttonCounts) {
            Map<String, Float> tmpMap = resultCountMap.get(buttonCount.getDate());
            if (tmpMap == null) {
                Map<String, Float> countMap = new HashMap<>();
                countMap.put("view_count", (float) buttonCount.getViewCount());
                countMap.put("click_count", (float) buttonCount.getClickCount());
                countMap.put("order_count", (float) buttonCount.getOrderCount());
                countMap.put("income", buttonCount.getIncome());
                resultCountMap.put(buttonCount.getDate(), countMap);
            } else {
                tmpMap.put("view_count", tmpMap.get("view_count") + buttonCount.getViewCount());
                tmpMap.put("click_count", tmpMap.get("click_count") + buttonCount.getClickCount());
                tmpMap.put("order_count", tmpMap.get("order_count") + buttonCount.getOrderCount());
                tmpMap.put("income", tmpMap.get("income") + buttonCount.getIncome());
            }
        }

        JSONArray viewCountJson = new JSONArray();
        JSONArray clickCountJson = new JSONArray();
        JSONArray orderCountJson = new JSONArray();
        JSONArray incomeCountJson = new JSONArray();
        JSONArray installCountJson = new JSONArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<String, Map<String, Float>> entry : resultCountMap.entrySet()) {
            if (Strings.isNullOrEmpty(entry.getKey()) || (entry.getValue() == null) || (entry.getValue().size() == 0)) {
                continue;
            }
            long dateTimestamps = strDateToTimestamps(entry.getKey(), simpleDateFormat);
            JSONArray viewJson = new JSONArray();
            viewJson.add(dateTimestamps);
            viewJson.add(entry.getValue().get("view"));
            viewCountJson.add(viewJson);

            JSONArray clickJson = new JSONArray();
            clickJson.add(dateTimestamps);
            clickJson.add(entry.getValue().get("click"));
            clickCountJson.add(clickJson);

            JSONArray orderJson = new JSONArray();
            orderJson.add(dateTimestamps);
            orderJson.add(entry.getValue().get("order"));
            orderCountJson.add(orderJson);

            JSONArray income = new JSONArray();
            income.add(dateTimestamps);
            income.add(entry.getValue().get("income"));
            incomeCountJson.add(income);

            JSONArray install = new JSONArray();
            install.add(dateTimestamps);
            install.add(0);
            installCountJson.add(install);
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("view", viewCountJson);
        resultJson.put("click", clickCountJson);
        resultJson.put("order", orderCountJson);
        resultJson.put("income", incomeCountJson);
        resultJson.put("install", installCountJson);
        return resultJson.toString();
    }

    public String getBtnTotalCounts(long appId, String btnId, String startDate, String endDate) {
        List<ButtonCount> buttonCounts = btnCountDao.getButtonCounts(appId, btnId, startDate, endDate);
        long view = 0, click = 0, order = 0;
        float income = 0;
        for (ButtonCount buttonCount : buttonCounts) {
            view += buttonCount.getViewCount();
            click += buttonCount.getClickCount();
            order += buttonCount.getOrderCount();
            income += buttonCount.getIncome();
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("view", view);
        resultJson.put("click", click);
        resultJson.put("order", order);
        resultJson.put("income", income);
        resultJson.put("install", 0);
        return resultJson.toString();
    }

    public String getBtnClickAndOrderCounts(SummaryButtonParams summaryButtonParams) {
        List<ButtonCount> buttonCounts = btnCountDao.getButtonCounts(summaryButtonParams.app_id, summaryButtonParams.btn_id,
                summaryButtonParams.start_date, summaryButtonParams.end_date);
        int openApp = 0;
        int openWeb = 0;
        int openOther = 0;
        for (ButtonCount buttonCount : buttonCounts) {
            openApp += buttonCount.getOpenAppCount();
            openWeb += buttonCount.getOpenWebCount();
            openOther += buttonCount.getOpenOtherCount();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("app", openApp);
        jsonObject.put("web", openWeb);
        jsonObject.put("other", openOther);
        return jsonObject.toString();
    }

    private static long strDateToTimestamps(String strDate, SimpleDateFormat simpleDateFormat) {
        try {
            Date date = simpleDateFormat.parse(strDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public Map<Long, Map<String, String>> getCounts(long[] ids) {
        Map<Long, Map<String, String>> counts = new HashMap<>(); // 获取短链的计数
        Map<Integer, List<Long>> dbIdsMap = deepLinkCountShardingSupport.getDbSharding(ids);
        for (Map.Entry<Integer, List<Long>> entry : dbIdsMap.entrySet()) {
            // TODO 后续可以改成多线程调用
            int db = entry.getKey();
            List<Long> idList = entry.getValue();
            JedisPort client = deepLinkCountShardingSupport.getClientByDb(db);
            List<Object> list = client.pipeline(new JedisPipelineReadCallback() {
                @Override
                public void call(JedisReadPipeline pipeline) {
                    for (long id : idList) {
                        pipeline.hgetAll(String.valueOf(id));
                    }
                }
            });
            for (int i = 0; i < idList.size(); i++) {
                Map<String, String> countMap = (Map<String, String>) list.get(i);
                if (CollectionUtils.isEmpty(countMap)) {
                    continue;
                }
                counts.put(idList.get(i), countMap);
            }
        }
        return counts;
    }

    public Map<String, String> getCount(long id) {
        JedisPort client = deepLinkCountShardingSupport.getClient(id);
        return client.hgetAll(String.valueOf(id));
    }

    class CountRank implements Comparable<CountRank> {
        public long app_id;
        public String app_name;
        public int rank;
        public int orders;
        public float income;
        public String orderBy;

        public String toJson() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("app_id", app_id);
            jsonObject.put("app_name", app_name);
            jsonObject.put("orders", orders);
            jsonObject.put("income", income);
            return jsonObject.toString();
        }

        @Override
        public int compareTo(CountRank o) {
            CountRank tmp = o;
            int result;
            if ("income".equals(orderBy)) {
                result = tmp.income > this.income ? 1 : (tmp.income == this.income ? 0 : -1);
            } else {
                result = tmp.orders > this.orders ? 1 : (tmp.orders == this.orders ? 0 : -1);
            }
            return result;
        }
    }

}
