package cc.linkedme.data.model;

import com.google.common.base.Strings;

import java.io.Serializable;

/**
 * Created by LinkedME01 on 16/3/25.
 */
public class DeepLinkCount implements Serializable{

    private long deepLinkId;

    private int ios_click;
    private int ios_install;
    private int ios_open;
    private int adr_click;
    private int adr_install;
    private int adr_open;

    private int pc_click;
    private int pc_ios_scan;
    private int pc_adr_scan;
    private int pc_ios_open;
    private int pc_adr_open;

    //先忽略
    private int pc_ios_install;
    private int pc_adr_install;

    public static enum CountType {
        ios_click("ic"),
        ios_install("ii"),
        ios_open("io"),

        adr_click("ac"),
        adr_install("ai"),
        adr_open("ao"),

        pc_click("pcc"),
        pc_scan("pcs"),
        pc_ios_scan("pcis"),
        pc_ios_install("pcii"),

        pc_ios_open("pcio"),
        pc_adr_scan("pcas"),
        pc_adr_install("pcai"),
        pc_adr_open("pcao");

        private String value;

        private CountType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static boolean isValid(CountType type) {
            boolean result = false;
            // if (type == other_adr_open) {
            // result = true;
            // }
            return result;
        }

    }

    public static boolean isValidCountType(String type) {
        if (CountType.ios_click.toString().equals(type) || CountType.ios_install.toString().equals(type)
                || CountType.ios_open.toString().equals(type)) {
            return true;
        }

        if (CountType.adr_click.toString().equals(type) || CountType.adr_install.toString().equals(type)
                || CountType.adr_open.toString().equals(type)) {
            return true;
        }

        if (CountType.pc_click.toString().equals(type) || CountType.pc_ios_scan.toString().equals(type)
                || CountType.pc_adr_scan.toString().equals(type)) {
            return true;
        }

        return false;
    }

    public static String getCountTypeFromOs(String os, String actionType) {
        String deviceType = null;
        if (!Strings.isNullOrEmpty(os)) {
            deviceType = os.trim().toLowerCase();
        }
        if ("android".equals(deviceType)) {
            deviceType = "adr";
        }
        String countType = deviceType + "_" + actionType;
        if (!DeepLinkCount.isValidCountType(countType)) {
            // TODO 对deviceType做判断
            countType = "other_" + actionType;
        }
        return countType;
    }

    public DeepLinkCount() {}

    public DeepLinkCount(long deepLinkId) {
        this.deepLinkId = deepLinkId;
    }

    public long getDeepLinkId() {
        return deepLinkId;
    }

    public void setDeepLinkId(long deepLinkId) {
        this.deepLinkId = deepLinkId;
    }
    public int getIos_click() {
        return ios_click;
    }

    public void setIos_click(int ios_click) {
        this.ios_click = ios_click;
    }

    public int getIos_install() {
        return ios_install;
    }

    public void setIos_install(int ios_install) {
        this.ios_install = ios_install;
    }

    public int getIos_open() {
        return ios_open;
    }

    public void setIos_open(int ios_open) {
        this.ios_open = ios_open;
    }

    public int getAdr_click() {
        return adr_click;
    }

    public void setAdr_click(int adr_click) {
        this.adr_click = adr_click;
    }

    public int getAdr_install() {
        return adr_install;
    }

    public void setAdr_install(int adr_install) {
        this.adr_install = adr_install;
    }

    public int getAdr_open() {
        return adr_open;
    }

    public void setAdr_open(int adr_open) {
        this.adr_open = adr_open;
    }

    public int getPc_click() {
        return pc_click;
    }

    public void setPc_click(int pc_click) {
        this.pc_click = pc_click;
    }

    public int getPc_ios_scan() {
        return pc_ios_scan;
    }

    public void setPc_ios_scan(int pc_ios_scan) {
        this.pc_ios_scan = pc_ios_scan;
    }

    public int getPc_ios_install() {
        return pc_ios_install;
    }

    public void setPc_ios_install(int pc_ios_install) {
        this.pc_ios_install = pc_ios_install;
    }

    public int getPc_ios_open() {
        return pc_ios_open;
    }

    public void setPc_ios_open(int pc_ios_open) {
        this.pc_ios_open = pc_ios_open;
    }

    public int getPc_adr_scan() {
        return pc_adr_scan;
    }

    public void setPc_adr_scan(int pc_adr_scan) {
        this.pc_adr_scan = pc_adr_scan;
    }

    public int getPc_adr_install() {
        return pc_adr_install;
    }

    public void setPc_adr_install(int pc_adr_install) {
        this.pc_adr_install = pc_adr_install;
    }

    public int getPc_adr_open() {
        return pc_adr_open;
    }

    public void setPc_adr_open(int pc_adr_open) {
        this.pc_adr_open = pc_adr_open;
    }

}
