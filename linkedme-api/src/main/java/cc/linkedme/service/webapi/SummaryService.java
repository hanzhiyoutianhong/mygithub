package cc.linkedme.service.webapi;

import cc.linkedme.commons.counter.component.CountComponent;
import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.util.Base62;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

        String onlineTime = "2016-04-01 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat( onlineTime );

        try {
            Date onlineDate = sdf.parse( "2016-04-01 00:00:00" );
            Date stDate = sdf.parse( start_date );
            Date edDate = sdf.parse( end_date );
            Date currentDate = sdf.parse( sdf.format( new Date() ) );

            if( stDate.after( currentDate ) || edDate.before( onlineDate ) ) {
                throw new LMException( LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE )
            }
            else {
                if( stDate.before( onlineDate ) )
                    start_date = onlineTime;
                if( edDate.after( currentDate ) )
                    end_date = currentDate.toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
        int endIndex = (summaryDeepLinkParams.returnNumber + startIndex) > deepLinkNum ? deepLinkNum: (summaryDeepLinkParams.returnNumber + startIndex);
        long[] tmpIds = new long[endIndex - startIndex];
        if(deepLinks.size() <= startIndex) {
            return null;
        }
        for(int i = startIndex; i < endIndex; i++) {
            tmpIds[i - startIndex] = deepLinks.get(i).getDeeplinkId();
        }
        Map<Long, Map<String, Integer>> counts = deepLinkCountComponent.getsAll(tmpIds);    //获取短链的计数

        JSONObject resultJson = new JSONObject();
        resultJson.put("total_count", deepLinkNum);
        JSONArray deepLinkJsonArr = new JSONArray();

        //遍历deepLinks,对应给count赋值(只遍历startIndex和endIndex之间的元素)
        List<DeepLink> resultDeepLinks = new ArrayList<>(deepLinkNum);
        for(int i = startIndex; i < endIndex; i++) {
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

    public String getDeepLinkInfoByDeepLinkId( SummaryDeepLinkParams summaryDeepLinkParams ) {
        DeepLink deepLinkInfo = deepLinkDao.getDeepLinkInfo( summaryDeepLinkParams.deepLinkId, summaryDeepLinkParams.appid );

        Map<String, Integer> count = deepLinkCountComponent.getAll( summaryDeepLinkParams.deepLinkId );

        DeepLinkCount deepLinkCount = new DeepLinkCount( summaryDeepLinkParams.deepLinkId );

        if( count != null ) {
            setDeepLinkCount( deepLinkCount, count );
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put( "deeplink_id", summaryDeepLinkParams.deepLinkId );

        String deeplink_url = "www.lkme.cc";
        deeplink_url += Base62.encode( deepLinkInfo.getAppId() );
        deeplink_url += Base62.encode( summaryDeepLinkParams.deepLinkId );
        resultJson.put( "deeplink_url", deeplink_url );

        int click = deepLinkCount.getAdr_click() + deepLinkCount.getIos_click() + deepLinkCount.getPc_click();
        int open = deepLinkCount.getAdr_open() + deepLinkCount.getIos_open() + deepLinkCount.getPc_adr_open() + deepLinkCount.getPc_ios_open();
        int install = deepLinkCount.getAdr_install() + deepLinkCount.getIos_install() + deepLinkCount.getPc_adr_install() + deepLinkCount.getPc_ios_install();
        resultJson.put( "click", click );
        resultJson.put( "open", open );
        resultJson.put( "install", install );
        resultJson.put( "feature", deepLinkInfo.getFeature() );
        resultJson.put( "campaign", deepLinkInfo.getCampaign() );
        resultJson.put( "stage", deepLinkInfo.getStage() );
        resultJson.put( "channel", deepLinkInfo.getChannel() );
        resultJson.put( "unique", "0" );
        resultJson.put( "tag", deepLinkInfo.getTags() );
        resultJson.put( "creation_time", deepLinkInfo.getCreateTime() );
        //TODO Judge source type
        resultJson.put( "creation_type", deepLinkInfo.getSource() );

        return resultJson.toString();
    }

    public int[] getDeepLikCounts( long deepLinkId ) {
        int[] res = new int[3];

        Map<String, Integer> count = deepLinkCountComponent.getAll( deepLinkId );

        DeepLinkCount deepLinkCount = new DeepLinkCount( deepLinkId );

        if( count != null ) {
            setDeepLinkCount( deepLinkCount, count );
        }

        res[0] = deepLinkCount.getPc_click() + deepLinkCount.getIos_click() + deepLinkCount.getAdr_click();
        res[1] = deepLinkCount.getIos_open() + deepLinkCount.getAdr_open() + deepLinkCount.getPc_adr_open() + deepLinkCount.getPc_ios_open();
        res[2] = deepLinkCount.getPc_ios_install() + deepLinkCount.getAdr_install() + deepLinkCount.getIos_install() + deepLinkCount.getPc_adr_install();

        return res;
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
                        if(countMap != null) {
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
        dplc.setPc_ios_install(dplCountMap.get(DeepLinkCount.CountType.pc_ios_install));
        dplc.setPc_ios_open(dplCountMap.get(DeepLinkCount.CountType.pc_ios_open));
        dplc.setPc_adr_scan(dplCountMap.get(DeepLinkCount.CountType.pc_adr_scan));
        dplc.setPc_adr_install(dplCountMap.get(DeepLinkCount.CountType.pc_adr_install));
        dplc.setPc_adr_open(dplCountMap.get(DeepLinkCount.CountType.pc_adr_open));
    }
}
