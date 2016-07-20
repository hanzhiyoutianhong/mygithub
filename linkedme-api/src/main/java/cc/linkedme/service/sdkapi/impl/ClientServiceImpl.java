package cc.linkedme.service.sdkapi.impl;

import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.service.sdkapi.ClientService;

import javax.annotation.Resource;

/**
 * Created by LinkedME01 on 16/3/20.
 */
public class ClientServiceImpl implements ClientService {
    @Resource
    private ClientDao clientDao;

    public int addClient(ClientInfo clientInfo) {
        int result = clientDao.addClient(clientInfo);
        return result;
    }
}
