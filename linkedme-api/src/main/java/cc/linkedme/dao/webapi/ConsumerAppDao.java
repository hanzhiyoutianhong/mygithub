package cc.linkedme.dao.webapi;

import cc.linkedme.data.model.ConsumerAppInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by LinkedME01 on 16/4/8.
 */
public interface ConsumerAppDao {
    int insertApp(ConsumerAppInfo consumerAppInfo);
    ConsumerAppInfo getConsumerAppInfo(long consumerAppId);
    Map<Long, ConsumerAppInfo> getConsumerAppList(List<Long> appIds);
    List<ConsumerAppInfo> getAllConsumerApps();
}
