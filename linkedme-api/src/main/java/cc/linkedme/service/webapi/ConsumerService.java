package cc.linkedme.service.webapi;

import cc.linkedme.commons.memcache.MemCacheTemplate;
import cc.linkedme.commons.serialization.KryoSerializationUtil;
import cc.linkedme.dao.webapi.ConsumerAppDao;
import cc.linkedme.data.model.ConsumerAppInfo;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.esotericsoftware.kryo.KryoException;
import com.google.gson.Gson;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by LinkedME01 on 16/4/8.
 */

@Service
public class ConsumerService {
    
    @Resource
    ConsumerAppDao consumerAppDao;
    @Resource
    private MemCacheTemplate<byte[]> consumerAppInfoMemCache;

    public long createConsumerApp() {
        return 0;
    }
    
    public List<ConsumerAppInfo> getAllConsumerApp() {
        List<ConsumerAppInfo> consumerAppInfoList = consumerAppDao.getAllConsumerApps();
        return consumerAppInfoList;
    }
    
    public ConsumerAppInfo getConsumerAppInfo(long consumerAppId){
        
        String consumerAppIdStr = String.valueOf(consumerAppId);
        Gson gson = new Gson();
        ConsumerAppInfo consumerAppInfo = null;
        byte[] consumerAppInfoByteArray = consumerAppInfoMemCache.get(consumerAppIdStr);
        if(!ArrayUtils.isEmpty(consumerAppInfoByteArray)){
            try{
                String consumerAppInfoStr = KryoSerializationUtil.deserializeObj(consumerAppInfoByteArray, String.class);
                consumerAppInfo = gson.fromJson(consumerAppInfoStr, ConsumerAppInfo.class);
            } catch(KryoException e) {
                consumerAppInfo = null;
                consumerAppInfoMemCache.delete(consumerAppIdStr);
            }
            if(consumerAppInfo != null){
                return consumerAppInfo;
            }
        }
        
        consumerAppInfo = consumerAppDao.getConsumerAppInfo(consumerAppId);
        if(consumerAppInfo != null){
            setConsumerAppInfoToCache(consumerAppInfo);
            return consumerAppInfo;
        }
        
        return null;
        
    }
    
    private boolean setConsumerAppInfoToCache(ConsumerAppInfo consumerAppInfo){
        Gson gson = new Gson();
        String gsonStr = gson.toJson(consumerAppInfo);
        byte[] b = KryoSerializationUtil.serializeObj(gsonStr);
        
        String consumerAppId = String.valueOf(consumerAppInfo.getAppId());
        boolean result = consumerAppInfoMemCache.set(consumerAppId, b);
        return result;
    }
    
}
