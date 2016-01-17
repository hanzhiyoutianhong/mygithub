package cc.linkedme.commons.redis.clients.jedis;

import cc.linkedme.commons.redis.clients.jedis.exceptions.JedisException;

public abstract class TransactionBlock extends Transaction {
    public TransactionBlock(Client client) {
        super(client);
    }

    public TransactionBlock() {
    }

    public abstract void execute() throws JedisException;

    public void setClient(Client client) {
        this.client = client;
    }
}
