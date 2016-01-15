package cc.linkedme.dao;

import cc.linkedme.model.LMClientInstallEntity;

/**
 * Created by qipo on 15/9/1.
 */
public interface LMClientInstallDao {

    void addClientInstall(LMClientInstallEntity lmRegisterInstallEntity);

    LMClientInstallEntity getOneClientInstall(String identityId);
}
