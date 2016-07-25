package cc.linkedme.enums;

/**
 * 
 * 终端设备的平台枚举类
 * 
 * @author tianjunfeng
 *
 */
public enum TerminatorPlatform {

    IOS("ios"), ANDROID("android"), WEB("web"), OTHER("other");

    public String value;

    private TerminatorPlatform(String value) {
        this.value = value;
    }

    public static TerminatorPlatform enumOf(String value) {

        value = value.toLowerCase();

        TerminatorPlatform[] allPlatform = TerminatorPlatform.values();
        for (TerminatorPlatform platform : allPlatform) {
            if (platform.value.equals(value)) {
                return platform;
            }
        }

        return null;
    }

}
