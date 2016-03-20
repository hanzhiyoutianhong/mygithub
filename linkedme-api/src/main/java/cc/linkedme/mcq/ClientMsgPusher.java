package cc.linkedme.mcq;

import javax.annotation.Resource;

import cc.linkedme.commons.mcq.forward.MsgForwarder;
import cc.linkedme.commons.mcq.writer.McqBaseWriter;
import cc.linkedme.commons.redis.clients.jedis.Client;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.DeepLink;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public class ClientMsgPusher {

    @Resource
    private McqBaseWriter apiMcqWriter;

    public void addClient(ClientInfo clientInfo) {
        String clientMsg = MsgUtils.toClientMsgJson(clientInfo);
        apiMcqWriter.writeMsg(clientMsg);
    }

    public void setApiMcqWriter(McqBaseWriter apiMcqWriter) {
        this.apiMcqWriter = apiMcqWriter;
    }
}
