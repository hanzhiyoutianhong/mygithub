package cc.linkedme.service.webapi;

import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinkedME01 on 16/3/20.
 */

@Service
public class SummaryService {

    @Resource
    DeepLinkDao deepLinkDao;

    public String getDeepLinkSummary(SummaryDeepLinkParams summaryDeepLinkParams) {
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


        return null;
    }
}
