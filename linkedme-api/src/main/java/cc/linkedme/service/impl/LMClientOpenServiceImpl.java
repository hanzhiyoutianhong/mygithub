package cc.linkedme.service.impl;

import cc.linkedme.dao.LMClientOpenDao;
import cc.linkedme.exception.LMException;
import cc.linkedme.exception.LMExceptionFactor;
import cc.linkedme.model.LMClientOpenEntity;
import cc.linkedme.service.LMClientOpenService;

/**
 * Created by qipo on 15/9/1.
 */
public class LMClientOpenServiceImpl implements LMClientOpenService {

    private LMClientOpenDao lmRegisterOpenDao;

    public void addClientOpen(LMClientOpenEntity lmClientOpenEntity) {
        try {
            lmRegisterOpenDao.addClientOpen(lmClientOpenEntity);
        } catch (Exception e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".addClientOpen");
        }
    }

    /**
     * construction function
     */

    public LMClientOpenServiceImpl() {

    }

    /**
     * get and set function
     */

    public void setLmRegisterOpenDao(LMClientOpenDao lmRegisterOpenDao) {
        this.lmRegisterOpenDao = lmRegisterOpenDao;
    }
}
