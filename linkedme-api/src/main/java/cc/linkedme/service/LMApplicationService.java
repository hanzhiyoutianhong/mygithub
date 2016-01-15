package cc.linkedme.service;

import cc.linkedme.model.LMApplicationEntity;

import java.util.List;

/**
 * Created by qipo on 15/9/3.
 */
public interface LMApplicationService {

    void addApplication(LMApplicationEntity lmApplicationEntity);

    LMApplicationEntity getOneApplicationLive(String app_key_live);

    List<LMApplicationEntity> getAllApplication();

}
