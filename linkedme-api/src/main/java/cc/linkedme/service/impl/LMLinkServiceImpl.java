package cc.linkedme.service.impl;

import cc.linkedme.dao.LMLinkDao;
import cc.linkedme.exception.LMException;
import cc.linkedme.exception.LMExceptionFactor;
import cc.linkedme.model.LMLinkEntity;
import cc.linkedme.service.LMLinkService;

import java.util.List;

/**
 * Created by qipo on 15/9/1.
 */
public class LMLinkServiceImpl implements LMLinkService {

    private LMLinkDao lmLinkDao;

    public String deepLink(String linkClickId) {
        String result = null;

        return result;
    }


    public void addLink(LMLinkEntity lmLinkEntity) {
        lmLinkDao.addLink(lmLinkEntity);
    }


    public List<LMLinkEntity> getAllLink() {
        List<LMLinkEntity> allRowsList;
        try {
            allRowsList = lmLinkDao.getAllLink();
        } catch (Exception e) {

            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".getAllLink");
        }
        return allRowsList;
    }

    public LMLinkEntity getOneLink(String linkClickId) {
        LMLinkEntity rowResult;
        try {
            rowResult = lmLinkDao.getOneLink(linkClickId);
        } catch (Exception e) {

            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".getOneLink");
        }
        return rowResult;
    }

    public void updateInstallLink(String clickId) {
        try {
            lmLinkDao.updateInstallLink(clickId);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".updateInstallLink");
        }
    }

    public void updateOpenLink(String clickId) {
        try {
            lmLinkDao.updateOpenLink(clickId);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".updateOpenLink");
        }
    }

    public void updateClickLink(String clickId) {
        try {
            lmLinkDao.updateClickLink(clickId);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".updateClickLink");
        }
    }

    public void updateWeChatLink(String clickId) {
        try {
            lmLinkDao.updateWeChatLink(clickId);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".updateWeChatLink");
        }
    }

    public void updateWeiboLink(String clickId) {
        try {
            lmLinkDao.updateWeiboLink(clickId);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".updateWeiboLink");
        }
    }

    public void updateRejectInstallLink(String clickId) {
        try {
            lmLinkDao.updateRejectInstallLink(clickId);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".updateRejectInstallLink");
        }
    }

    /**
     * construction  function
     */

    public LMLinkServiceImpl() {

    }

    /**
     * get and set function
     */

    public void setLmLinkDao(LMLinkDao lmLinkDao) {
        this.lmLinkDao = lmLinkDao;
    }
}
