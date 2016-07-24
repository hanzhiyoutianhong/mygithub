package cc.linkedme.enums;

/**
 * button的计数类型枚举类
 * 
 * @author tianjunfeng
 *
 */
public enum BtnCountType {

    VIEW("_view_count"), CLICK("_click_count");

    public String value;

    private BtnCountType(String value) {
        this.value = value;
    }
}
