package cc.linkedme.service.impl;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.params.LMCloseParams;
import cc.linkedme.data.model.params.LMInstallParams;
import cc.linkedme.data.model.params.LMOpenParams;
import cc.linkedme.data.model.params.LMUrlParams;
import cc.linkedme.exception.LMException;
import cc.linkedme.exception.LMExceptionFactor;
import cc.linkedme.mcq.DeepLinkMsgPusher;
import cc.linkedme.mcq.MsgUtils;
import cc.linkedme.service.LMSdkService;
import com.google.common.base.Joiner;

import javax.annotation.Resource;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public class LMSdkServiceImpl implements LMSdkService {
    @Resource
    public UuidCreator uuidCreator;

    @Resource
    public DeepLinkMsgPusher deepLinkMsgPusher;
    public String install(LMInstallParams lmInstallParams) {
        String result = null;

        try {

            //hardware_id equals identify_id, and identify_id and link_click_id are in the redis

            //browser_fingerprint_id equals device_fingerprint_id

            //add the info into mysql

        } catch(Exception e) {
            //error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".install");

        }

        //info log
        ApiLogger.info("");


        return result;
    }

    public String open(LMOpenParams lmOpenParams) {
        String result = null;

        try {

            //get the linkIdentifier by redis

            //add the info into mysql

        } catch(Exception e) {
            //error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".open");

        }

        //info log
        ApiLogger.info("");

        return result;
    }

    public String close(LMCloseParams lmCloseParams) {

        String result = null;

        try {

            //add this into mysql

        } catch(Exception e) {
            //error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".close");

        }

        //info log
        ApiLogger.info("");

        return result;
    }

    public String url(LMUrlParams lmUrlParams) {
        Joiner joiner = Joiner.on("&").skipNulls();
        String urlParamsStr = joiner.join(lmUrlParams.linkedmeKey, lmUrlParams.tags, lmUrlParams.alias, lmUrlParams.channel, lmUrlParams.feature, lmUrlParams.stage, lmUrlParams.params);
        String deepLinkMd5 = MD5Utils.md5(urlParamsStr);
        //从redis里查找md5是否存在
        //如果存在,找出对应的deeplink_id,base62进行编码,
        // 根据linkedmeKey从redis里查找出appIdentifier,生成短链,返回 //http://lkme.cc/abc/qwerk
        //如果不存在,发号deeplink_id,在redis里保存md5和deeplink_id的键值对
        //然后把消息写入队列, 返回短链
        DeepLink link = new DeepLink(uuidCreator.nextId(0), lmUrlParams.linkedmeKey, deepLinkMd5, lmUrlParams.tags, lmUrlParams.alias, lmUrlParams.channel, lmUrlParams.feature, lmUrlParams.stage, lmUrlParams.campaign, lmUrlParams.params, lmUrlParams.source, lmUrlParams.sdkVersion);
        String deepLinkMsgJson = MsgUtils.toDeepLinkMsgJson(link);
        deepLinkMsgPusher.addDeepLink(link);
        String result = null;


        return result;  //linkedme_key & tags & alias & channel & feature & stage & params
    }

    public String preInstall(String linkClickId) {
        String result = null;

        try {

            //set identify_id for browser,

        } catch(Exception e) {
            //error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".preInstall");
        }

        //info log
        ApiLogger.info("");


        return result;
    }


}
