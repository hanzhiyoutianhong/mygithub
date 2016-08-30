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

    public static RequestEnv enumOf(String value){

        value = value.toLowerCase();

        RequestEnv[] requestEnvs = RequestEnv.values();
        for(RequestEnv requestEnv : requestEnvs){
            if(requestEnv.value.equals(value)){
                return requestEnv;
            }
        }
        return null;
    }

    public String getValue(){
        return value;
    }
}
