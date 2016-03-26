package cc.linkedme.data.model;

/**
 * Created by LinkedME01 on 16/3/25.
 */
public class DeepLinkCount {

    private long deepLinkId;
    private int wx_ios_click;
    private int wx_ios_install;
    private int wx_ios_open;
    private int wx_adr_click;
    private int wx_adr_install;
    private int wx_adr_open;

    private int wb_ios_click;
    private int wb_ios_install;
    private int wb_ios_open;
    private int wb_adr_click;
    private int wb_adr_install;
    private int wb_adr_open;

    private int pc_click;
    private int pc_ios_scan;
    private int pc_ios_install;
    private int pc_ios_open;
    private int pc_adr_scan;
    private int pc_adr_install;
    private int pc_adr_open;

    private int other_ios_click;
    private int other_ios_install;
    private int other_ios_open;
    private int other_adr_click;
    private int other_adr_install;
    private int other_adr_open;

    public static enum CountType {
        wx_ios_click("wxic"), wx_ios_install("wxii"), wx_ios_open("wxio"), wx_adr_click("wxac"), wx_adr_install("wxai"), wx_adr_open(
                "wxao"),

        wb_ios_click("wbic"), wb_ios_install("wbii"), wb_ios_open("wbio"), wb_adr_click("wbac"), wb_adr_install("wbai"), wb_adr_open(
                "wbao"),

        pc_click("pcc"), pc_ios_scan("pcis"), pc_ios_install("pcii"), pc_ios_open("pcio"), pc_adr_scan("pcas"), pc_adr_install(
                "pcai"), pc_adr_open("pcao"),

        other_ios_click("oic"), other_ios_install("oii"), other_ios_open("oio"), other_adr_click("oac"), other_adr_install(
                "oai"), other_adr_open("oao");

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
        if (CountType.wx_ios_click.toString().equals(type) || CountType.wx_ios_install.toString().equals(type)
                || CountType.wx_ios_open.toString().equals(type)) {
            return true;
        }
        if (CountType.wx_adr_click.toString().equals(type) || CountType.wx_adr_install.toString().equals(type)
                || CountType.wx_adr_open.toString().equals(type)) {
            return true;
        }
        if (CountType.wb_ios_click.toString().equals(type) || CountType.wb_ios_install.toString().equals(type)
                || CountType.wb_ios_open.toString().equals(type)) {
            return true;
        }
        if (CountType.wb_adr_click.toString().equals(type) || CountType.wb_adr_install.toString().equals(type)
                || CountType.wb_adr_open.toString().equals(type)) {
            return true;
        }
        if (CountType.pc_click.toString().equals(type) || CountType.pc_ios_scan.toString().equals(type)
                || CountType.pc_ios_install.toString().equals(type) || CountType.pc_ios_open.toString().equals(type)) {
            return true;
        }
        if (CountType.pc_adr_scan.toString().equals(type) || CountType.pc_adr_install.toString().equals(type)
                || CountType.pc_adr_open.toString().equals(type)) {
            return true;
        }
        return false;
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

    public int getWx_ios_click() {
        return wx_ios_click;
    }

    public void setWx_ios_click(int wx_ios_click) {
        this.wx_ios_click = wx_ios_click;
    }

    public int getWx_ios_install() {
        return wx_ios_install;
    }

    public void setWx_ios_install(int wx_ios_install) {
        this.wx_ios_install = wx_ios_install;
    }

    public int getWx_ios_open() {
        return wx_ios_open;
    }

    public void setWx_ios_open(int wx_ios_open) {
        this.wx_ios_open = wx_ios_open;
    }

    public int getWx_adr_click() {
        return wx_adr_click;
    }

    public void setWx_adr_click(int wx_adr_click) {
        this.wx_adr_click = wx_adr_click;
    }

    public int getWx_adr_install() {
        return wx_adr_install;
    }

    public void setWx_adr_install(int wx_adr_install) {
        this.wx_adr_install = wx_adr_install;
    }

    public int getWx_adr_open() {
        return wx_adr_open;
    }

    public void setWx_adr_open(int wx_adr_open) {
        this.wx_adr_open = wx_adr_open;
    }

    public int getWb_ios_click() {
        return wb_ios_click;
    }

    public void setWb_ios_click(int wb_ios_click) {
        this.wb_ios_click = wb_ios_click;
    }

    public int getWb_ios_install() {
        return wb_ios_install;
    }

    public void setWb_ios_install(int wb_ios_install) {
        this.wb_ios_install = wb_ios_install;
    }

    public int getWb_ios_open() {
        return wb_ios_open;
    }

    public void setWb_ios_open(int wb_ios_open) {
        this.wb_ios_open = wb_ios_open;
    }

    public int getWb_adr_click() {
        return wb_adr_click;
    }

    public void setWb_adr_click(int wb_adr_click) {
        this.wb_adr_click = wb_adr_click;
    }

    public int getWb_adr_install() {
        return wb_adr_install;
    }

    public void setWb_adr_install(int wb_adr_install) {
        this.wb_adr_install = wb_adr_install;
    }

    public int getWb_adr_open() {
        return wb_adr_open;
    }

    public void setWb_adr_open(int wb_adr_open) {
        this.wb_adr_open = wb_adr_open;
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

    public int getOther_ios_click() {
        return other_ios_click;
    }

    public void setOther_ios_click(int other_ios_click) {
        this.other_ios_click = other_ios_click;
    }

    public int getOther_ios_install() {
        return other_ios_install;
    }

    public void setOther_ios_install(int other_ios_install) {
        this.other_ios_install = other_ios_install;
    }

    public int getOther_ios_open() {
        return other_ios_open;
    }

    public void setOther_ios_open(int other_ios_open) {
        this.other_ios_open = other_ios_open;
    }

    public int getOther_adr_click() {
        return other_adr_click;
    }

    public void setOther_adr_click(int other_adr_click) {
        this.other_adr_click = other_adr_click;
    }

    public int getOther_adr_install() {
        return other_adr_install;
    }

    public void setOther_adr_install(int other_adr_install) {
        this.other_adr_install = other_adr_install;
    }

    public int getOther_adr_open() {
        return other_adr_open;
    }

    public void setOther_adr_open(int other_adr_open) {
        this.other_adr_open = other_adr_open;
    }
}
