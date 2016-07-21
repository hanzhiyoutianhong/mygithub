package cc.linkedme.mcq;

import javax.annotation.Resource;

import cc.linkedme.commons.json.JsonBuilder;
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
        JsonBuilder clientMsg = new JsonBuilder();
        clientMsg.append("type", 21);
        JsonBuilder infoJson = MsgUtils.toClientMsgJson(clientInfo);
        clientMsg.append("info", infoJson.flip());
        apiMcqWriter.writeMsg(clientMsg.flip().toString());
    }

}
