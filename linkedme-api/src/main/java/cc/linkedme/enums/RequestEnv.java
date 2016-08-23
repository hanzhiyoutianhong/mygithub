package cc.linkedme.enums;

/**
 * Created by tianjunfeng on 16-8-22.
 */
public enum RequestEnv {

    LIVE("live"), TEST("test");

    private String value;

    private RequestEnv(String value){
        this.value = value;
    }

}
