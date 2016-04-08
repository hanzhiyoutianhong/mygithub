package cc.linkedme.service.webapi;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.webapi.ButtonDao;
import cc.linkedme.dao.webapi.ConsumerAppDao;
import cc.linkedme.data.model.ButtonInfo;
import cc.linkedme.data.model.ConsumerAppInfo;
import cc.linkedme.data.model.params.ButtonParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LinkedME01 on 16/4/7.
 */

@Service
public class BtnService {
    @Resource
    ButtonDao buttonDao;

    @Resource
    ConsumerAppDao consumerAppDao;

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
        ButtonInfo btnInfo = buttonDao.getButtonInfo(btnId);
        ConsumerAppInfo consumerAppInfo = consumerAppDao.getConsumerAppInfo(btnInfo.getConsumerAppId());
        btnInfo.setConsumerAppInfo(consumerAppInfo);
        return btnInfo;
    }

    public List<ButtonInfo> getButtons(long appId) {
        //根据appId获取app的所有buttons
        List<ButtonInfo> buttons = buttonDao.getButtonList(appId);
        List<Long> consumerAppIds = new ArrayList<>(buttons.size());
        for(ButtonInfo btn : buttons) {
            if(btn != null) {
                consumerAppIds.add(btn.getConsumerAppId());
            }
        }

        //根据buttons对应的consumerAppId获取button对应的ConsumerApp信息
        Map<Long, ConsumerAppInfo> consumerAppInfos = consumerAppDao.getConsumerAppList(consumerAppIds);

        for(ButtonInfo btn : buttons) {
            btn.setConsumerAppInfo(consumerAppInfos.get(btn.getConsumerAppId()));
        }
        if (buttons.size() > 0) {
            return buttons;
        }
        return new ArrayList<ButtonInfo>();
    }

}
