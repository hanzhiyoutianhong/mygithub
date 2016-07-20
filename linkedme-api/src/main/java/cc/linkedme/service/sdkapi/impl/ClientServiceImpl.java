package cc.linkedme.service.sdkapi.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.DeepLinkCount;
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
    private ShardingSupportHash<JedisPort> deepLinkCountShardingSupport;

    @Resource
    private ShardingSupportHash<JedisPort> linkedmeKeyShardingSupport;

    public static ThreadPoolExecutor deepLinkCountThreadPool = new ThreadPoolExecutor(20, 20, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(300), new ThreadPoolExecutor.DiscardOldestPolicy());

    public int addClient(ClientInfo clientInfo) {
        int result = clientDao.addClient(clientInfo);
        return result;
    }
}
