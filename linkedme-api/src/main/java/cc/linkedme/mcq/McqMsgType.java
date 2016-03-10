package cc.linkedme.mcq;

public enum McqMsgType {

    ADD_DEEPLINK(11),
    UPDATE_DEEPLINK(12),
    DELETE_DEEPLINK(13);

    private int type;

    private McqMsgType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
    }