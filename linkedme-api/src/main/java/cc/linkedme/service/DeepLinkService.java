package cc.linkedme.service;

import cc.linkedme.dao.sdkapi.DeepLinkDao;
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

    public int addDeepLink(DeepLink deepLink) {
        int result = 0;
        // insert mysql
        result = deepLinkDao.addDeepLink(deepLink);
        // counter
        return result;
    }
}
