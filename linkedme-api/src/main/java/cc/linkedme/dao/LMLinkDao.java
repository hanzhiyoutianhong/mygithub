package cc.linkedme.dao;

import cc.linkedme.model.LMLinkEntity;

import java.util.List;


/**
 * Created by qipo on 15/9/1.
 */
public interface LMLinkDao {

    void addLink(LMLinkEntity lmLinkEntity);

    List<LMLinkEntity> getAllLink();

    LMLinkEntity getOneLink(String linkClickId);

    void updateInstallLink(String linkClickId);

    void updateOpenLink(String linkClickId);

    void updateClickLink(String linkClickId);

    void updateWeChatLink(String linkClickId);

    void updateWeiboLink(String linkClickId);

    void updateRejectInstallLink(String linkClickId);


}
