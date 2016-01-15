package cc.linkedme.service.impl;

import cc.linkedme.dao.LMApplicationDao;
import cc.linkedme.exception.LMException;
import cc.linkedme.exception.LMExceptionFactor;
import cc.linkedme.model.LMApplicationEntity;
import cc.linkedme.service.LMApplicationService;

import java.util.List;

/**
 * Created by qipo on 15/9/3.
 */
public class LMApplicationServiceImpl implements LMApplicationService {

    private LMApplicationDao addApplicationDao;

    public void addApplication(LMApplicationEntity lmApplicationEntity) {
        try {
            addApplicationDao.addApplication(lmApplicationEntity);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".addApplication");
        }
    }

    public List<LMApplicationEntity> getAllApplication() {
        List<LMApplicationEntity> resultList;
        try {
             resultList = addApplicationDao.getAllApplication();
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".getAllAppIdUrlScheme");
        }

        return resultList;
    }


    public LMApplicationEntity getOneApplicationLive(String appKey) {
        LMApplicationEntity lmAppIDUrlSchemeEntity;
        try {
            lmAppIDUrlSchemeEntity = addApplicationDao.getOneApplicationLive(appKey);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".getOneAppIdUrlScheme");
        }

        return  lmAppIDUrlSchemeEntity;
    }

    /**
     * construction function
     */

    public LMApplicationServiceImpl() {
    }

    /**
     * get and set function
     */
    public void setAddApplicationDao(LMApplicationDao addApplicationDao) {
        this.addApplicationDao = addApplicationDao;
    }
}
