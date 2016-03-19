package cc.linkedme.service;

import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.dao.sdkapi.DeepLinkParamDao;
import cc.linkedme.data.model.DeepLink;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by LinkedME01 on 16/3/10.
 */

@Service
public class DeepLinkService {
    @Resource
    private DeepLinkDao deepLinkDao;

    @Resource
    private DeepLinkParamDao deepLinkParamDao;

    public int addDeepLink(DeepLink deepLink) {
        int result = 0;
        // insert deepLink table;
        result = deepLinkDao.addDeepLink(deepLink);

        //insert deeplinkparam table
        result = deepLinkParamDao.addDeepLinkParam(deepLink);
        // counter
        return result;
    }
}
