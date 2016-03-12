package cc.linkedme.service.impl;

import cc.linkedme.common.params.LMCloseParams;
import cc.linkedme.common.params.LMInstallParams;
import cc.linkedme.common.params.LMOpenParams;
import cc.linkedme.common.params.LMUrlParams;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.exception.LMException;
import cc.linkedme.exception.LMExceptionFactor;
import cc.linkedme.service.LMSdkService;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public class LMSdkServiceImpl implements LMSdkService {


    public String install(LMInstallParams lmInstallParams) {
        String result = null;

        try {

            // hardware_id equals identify_id, and identify_id and link_click_id are in the redis

            // browser_fingerprint_id equals device_fingerprint_id

            // add the info into mysql

        } catch (Exception e) {
            // error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".install");

        }

        // info log
        ApiLogger.info("");


        return result;
    }

    public String open(LMOpenParams lmOpenParams) {
        String result = null;

        try {

            // get the linkIdentifier by redis

            // add the info into mysql

        } catch (Exception e) {
            // error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".open");

        }

        // info log
        ApiLogger.info("");

        return result;
    }

    public String close(LMCloseParams lmCloseParams) {

        String result = null;

        try {

            // add this into mysql

        } catch (Exception e) {
            // error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".close");

        }

        // info log
        ApiLogger.info("");

        return result;
    }

    public String url(LMUrlParams lmUrlParams) {
        String result = null;

        try {

            // generate a link by parameters

            // add this into the redis

        } catch (Exception e) {
            // error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".url");

        }

        // info log
        ApiLogger.info("");

        return result;
    }

    public String preInstall(String linkClickId) {
        String result = null;

        try {

            // set identify_id for browser,

        } catch (Exception e) {
            // error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".preInstall");
        }

        // info log
        ApiLogger.info("");


        return result;
    }


}
