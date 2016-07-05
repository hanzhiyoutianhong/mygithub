package cc.linkedme.mcq;

public enum McqMsgType {

    ADD_DEEPLINK(11), UPDATE_DEEPLINK(12), DELETE_DEEPLINK(13),
    ADD_CLIENT(21), UPDATE_CLIENT(22), DELETE_CLIENT(23),
    ADD_DEEPLINK_COUNT(31),
    ADD_FINGER_PRINT(41), UPDATE_FINGER_PRINT(42);

    private int type;

    private McqMsgType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
