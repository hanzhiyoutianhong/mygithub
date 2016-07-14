package cc.linkedme.service.webapi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.memcache.MemCacheTemplate;
import cc.linkedme.commons.serialization.KryoSerializationUtil;
import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.webapi.ButtonDao;
import cc.linkedme.dao.webapi.ConsumerAppDao;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.ConsumerAppInfo;
import cc.linkedme.data.model.params.ButtonParams;

import com.esotericsoftware.kryo.KryoException;
import com.google.gson.Gson;

/**
 * Created by LinkedME01 on 16/4/7.
 */

@Service
public class BtnService {
    @Resource
    ButtonDao buttonDao;

    @Resource
    ConsumerAppDao consumerAppDao;
    
    @Resource
    private MemCacheTemplate<byte[]> btnInfoMemCache;
  
    public String createBtn(ButtonParams buttonParams) {
        String btnId = Util.getUUID();
        ButtonInfo buttonInfo = new ButtonInfo();
        buttonInfo.setBtnId(btnId);
        buttonInfo.setAppId(buttonParams.app_id);
        buttonInfo.setBtnName(buttonParams.button_name);
        buttonInfo.setBtnCategory(buttonParams.btn_category);
        buttonInfo.setConsumerAppId(buttonParams.consumer_app_id);
        buttonInfo.setCheckStatus(buttonParams.check_status);
        buttonInfo.setOnlineStatus(buttonParams.online_status);

        int result = buttonDao.insertButton(buttonInfo);
        if (result > 0) {
            return btnId;
        } else {
            throw new LMException(LMExceptionFactor.LM_SYS_ERROR, "create button failed");
        }
    }

    public ButtonInfo getBtnInfo(String btnId) {

        Gson gson = new Gson();
        ButtonInfo btnInfo = null;
        byte[] btnInfoByteArray = btnInfoMemCache.get(btnId);
        if (!ArrayUtils.isEmpty(btnInfoByteArray)) {
            try {
                String btnInfoStr = KryoSerializationUtil.deserializeObj(btnInfoByteArray, String.class);
                btnInfo = gson.fromJson(btnInfoStr, ButtonInfo.class);
            } catch (KryoException e) {
                btnInfo = null;
                btnInfoMemCache.delete(btnId);
            }

            if(btnInfo != null){
                return btnInfo;
            }
        }

        btnInfo = buttonDao.getButtonInfo(btnId);
        if(btnInfo != null){
            setBtnInfoToCache(btnInfo);
            return btnInfo;
        }
        
        return null;
        
    }
    
   
    
    private boolean setBtnInfoToCache(ButtonInfo btnInfo){
        Gson gson = new Gson();
        String gsonStr = gson.toJson(btnInfo);
        
        byte[] b = KryoSerializationUtil.serializeObj(gsonStr);
        boolean result = btnInfoMemCache.set(btnInfo.getBtnId(), b);
        return result;
    }
    
    
    public List<ButtonInfo> getButtons(long appId) {
        // 根据appId获取app的所有在线的button
        List<ButtonInfo> buttons = buttonDao.getButtonListByAppId(appId, false);
        List<Long> consumerAppIds = new ArrayList<>(buttons.size());
        for (ButtonInfo btn : buttons) {
            if (btn != null) {
                consumerAppIds.add(btn.getConsumerAppId());
            }
        }
        if (consumerAppIds.size() != 0) {
            Map<Long, ConsumerAppInfo> consumerAppInfos = consumerAppDao.getConsumerAppList(consumerAppIds);

            for (ButtonInfo btn : buttons) {
                btn.setConsumerAppInfo(consumerAppInfos.get(btn.getConsumerAppId()));
            }
            if (buttons.size() > 0) {
                return buttons;
            }
        }
        // 根据buttons对应的consumerAppId获取button对应的ConsumerApp信息

        return new ArrayList<ButtonInfo>();
    }

    public List<ButtonInfo> getButtonsByBtnId(String btnId) {
        // 根据btnId获取此id对应的所有的历史button
        List<ButtonInfo> buttons = buttonDao.getButtonListByBtnId(btnId);
        return buttons;
    }

    public List<ButtonInfo> getAllButtonsByAppId(long appId) {
        // 根据appId获取所有的button
        List<ButtonInfo> buttons = buttonDao.getButtonListByAppId(appId, true);

        List<Long> consumerAppIds = new ArrayList<>(buttons.size());
        for (ButtonInfo btn : buttons) {
            if (btn != null) {
                consumerAppIds.add(btn.getConsumerAppId());
            }
        }

        // 根据buttons对应的consumerAppId获取button对应的ConsumerApp信息
        Map<Long, ConsumerAppInfo> consumerAppInfos = consumerAppDao.getConsumerAppList(consumerAppIds);
        for (ButtonInfo btn : buttons) {
            btn.setConsumerAppInfo(consumerAppInfos.get(btn.getConsumerAppId()));
        }
        return buttons;
    }

    public boolean deleteButton(String btnId) {
        return buttonDao.deleteButton(btnId);
    }

    public boolean updateButtonByBtnId(ButtonParams buttonParams) {
        ButtonInfo buttonInfo = new ButtonInfo();
        buttonInfo.setBtnId(buttonParams.button_id);
        buttonInfo.setAppId(buttonParams.app_id);
        buttonInfo.setBtnName(buttonParams.button_name);
        buttonInfo.setOnlineStatus(buttonParams.online_status);
        if(buttonDao.updateButton(buttonInfo)) {
            return true;
        } else {
            throw new LMException(LMExceptionFactor.LM_SYS_ERROR, "update button failed");
        }
    }
}
