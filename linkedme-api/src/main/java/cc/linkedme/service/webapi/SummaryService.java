package cc.linkedme.service.webapi;

import cc.linkedme.commons.counter.component.CountComponent;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.dao.webapi.BtnCountDao;
import cc.linkedme.data.dao.util.DateDuration;
import cc.linkedme.data.model.ButtonCount;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.DeepLinkCount;
import cc.linkedme.data.model.params.SummaryButtonParams;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by LinkedME01 on 16/3/20.
 */

@Service
public class SummaryService {
    @Resource
    DeepLinkDao deepLinkDao;

    @Resource
    BtnCountDao btnCountDao;

    @Resource
    private CountComponent deepLinkCountComponent;

    @Resource
    private BtnService btnService;

    @Resource
    ShardingSupportHash<JedisPort> btnCountShardingSupport;

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
        int deepLinkNum = deepLinks.size();
        int startIndex = summaryDeepLinkParams.skipNumber;
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
        Map<Long, Map<String, Integer>> counts = deepLinkCountComponent.getsAll(tmpIds); // 获取短链的计数

        JSONObject resultJson = new JSONObject();
        resultJson.put("total_count", deepLinkNum);
        JSONArray deepLinkJsonArr = new JSONArray();

        // 遍历deepLinks,对应给count赋值(只遍历startIndex和endIndex之间的元素)
        List<DeepLink> resultDeepLinks = new ArrayList<>(deepLinkNum);
        for (int i = startIndex; i < endIndex; i++) {
            long deepLinkId = deepLinks.get(i).getDeeplinkId();
            DeepLinkCount deepLinkCountObject = new DeepLinkCount(deepLinkId);
            Map<String, Integer> countMap = counts.get(deepLinkId);
            if (countMap != null && countMap.size() > 0) {
                setDeepLinkCount(deepLinkCountObject, countMap);
            }
            deepLinks.get(i).setDeepLinkCount(deepLinkCountObject);
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
                if (counts != null && counts.size() > 0) {
                    Map<Long, DeepLinkCount> dplMap = new HashMap<>(50);
                    for (int j = 0; j < 50; j++) {
                        DeepLinkCount deepLinkCount = new DeepLinkCount(tmpIds[j]);
                        Map<String, Integer> countMap = counts.get(tmpIds);
                        if (countMap != null) {
                            setDeepLinkCount(deepLinkCount, countMap);
                        }
                        dplMap.put(tmpIds[j], deepLinkCount);
                    }
                    deepLinkCountMap.putAll(dplMap);
                }
                tmpIds = new long[50];
            }
        }

        if (deepLinks.size() % 50 != 0) {
            Map<Long, Map<String, Integer>> counts = deepLinkCountComponent.getsAll(tmpIds);
            // 遍历counts,转成Map<Long, DeepLinkCount>
            if (counts != null && counts.size() > 0) {
                Map<Long, DeepLinkCount> dplMap = new HashMap<>(tmpIds.length);
                for (int j = 0; j < tmpIds.length; j++) {
                    DeepLinkCount deepLinkCount = new DeepLinkCount(tmpIds[j]);
                    Map<String, Integer> countMap = counts.get(tmpIds);
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

    private void setDeepLinkCount(DeepLinkCount dplc, Map<String, Integer> dplCountMap) {
        dplc.setIos_click(dplCountMap.get(DeepLinkCount.CountType.ios_click));
        dplc.setIos_install(dplCountMap.get(DeepLinkCount.CountType.ios_click.toString()));
        dplc.setIos_open(dplCountMap.get(DeepLinkCount.CountType.ios_open.toString()));
        dplc.setAdr_click(dplCountMap.get(DeepLinkCount.CountType.adr_click.toString()));
        dplc.setAdr_install(dplCountMap.get(DeepLinkCount.CountType.adr_install.toString()));
        dplc.setAdr_open(dplCountMap.get(DeepLinkCount.CountType.adr_open.toString()));

        dplc.setPc_click(dplCountMap.get(DeepLinkCount.CountType.pc_click));
        dplc.setPc_ios_scan(dplCountMap.get(DeepLinkCount.CountType.pc_ios_scan));
        dplc.setPc_adr_scan(dplCountMap.get(DeepLinkCount.CountType.pc_adr_scan));
        //dplc.setPc_ios_install(dplCountMap.get(DeepLinkCount.CountType.pc_ios_install));
        //dplc.setPc_ios_open(dplCountMap.get(DeepLinkCount.CountType.pc_ios_open));
        //dplc.setPc_adr_install(dplCountMap.get(DeepLinkCount.CountType.pc_adr_install));
        //dplc.setPc_adr_open(dplCountMap.get(DeepLinkCount.CountType.pc_adr_open));
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray resultJsonArray = new JSONArray();
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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JSONArray viewCountJson = new JSONArray();
        JSONArray clickCountJson = new JSONArray();
        JSONArray orderCountJson = new JSONArray();
        JSONArray incomeCountJson = new JSONArray();
        JSONArray installCountJson = new JSONArray();
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
