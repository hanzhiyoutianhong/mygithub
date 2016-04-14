package cc.linkedme.service.sdkapi.impl;

import cc.linkedme.commons.counter.component.CountComponent;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.sdkapi.ClientService;
import com.google.common.base.Strings;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by LinkedME01 on 16/3/20.
 */
public class ClientServiceImpl implements ClientService {
    @Resource
    private ClientDao clientDao;

    @Resource
    private DeepLinkService deepLinkService;

    @Resource
    private CountComponent deepLinkCountComponent;

    @Resource
    private ShardingSupportHash<JedisPort> clientShardingSupport;

    public static ThreadPoolExecutor deepLinkCountThreadPool = new ThreadPoolExecutor(20, 20, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(300), new ThreadPoolExecutor.DiscardOldestPolicy());

    public int addClient(ClientInfo clientInfo, long deepLinkId) {
        int result = clientDao.addClient(clientInfo);

        if (result > 0 && deepLinkId > 0) {
            long appId = 0; // 根据deepLinkId查找appId
            String value = clientShardingSupport.getClient(clientInfo.getLinkedmeKey()).get(clientInfo.getLinkedmeKey());
            if (!Strings.isNullOrEmpty(value)) {
                appId = Long.parseLong(value.split(",")[0]);
            }
            DeepLink deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);
            String countType = clientInfo.getOs() + "_install";
            // count
            deepLinkCountThreadPool.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        // TODO 对deeplink_id的有效性做判断
                        deepLinkCountComponent.incr(deepLinkId, countType, 1);
                    } catch (Exception e) {
                        ApiLogger.warn("ClientServiceImpl.addClient deepLinkCountThreadPool count failed", e);
                    }
                    return null;
                }
            });

        }
        return 0;
    }
}
