package cc.linkedme.service.impl;

import cc.linkedme.dao.LMClientInstallDao;
import cc.linkedme.exception.LMException;
import cc.linkedme.exception.LMExceptionFactor;
import cc.linkedme.model.LMClientInstallEntity;
import cc.linkedme.service.LMClientInstallService;

/**
 * Created by qipo on 15/9/1.
 */
public class LMClientInstallServiceImpl implements LMClientInstallService {


    private LMClientInstallDao lmClientInstallDao;

    public void addClientInstall(LMClientInstallEntity lmClientInstallEntity) {
        try {
            lmClientInstallDao.addClientInstall(lmClientInstallEntity);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".addClientInstall");
        }
    }

    public LMClientInstallEntity getOneClientInstall(String identityId) {
        LMClientInstallEntity rowResult;
        try {
            rowResult = lmClientInstallDao.getOneClientInstall(identityId);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".LMClientInstallEntity LMClientInstallEntity(" + identityId + ");");
        }
        return rowResult;
    }

    /**
     * construction function
     */

    public LMClientInstallServiceImpl() {

    }

    /**
     * get and set function
     */
    public void setLmClientInstallDao(LMClientInstallDao lmClientInstallDao) {
        this.lmClientInstallDao = lmClientInstallDao;
    }
}
