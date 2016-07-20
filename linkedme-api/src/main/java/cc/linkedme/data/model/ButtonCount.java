package cc.linkedme.data.model;


/**
 * Created by LinkedME01 on 16/4/12.
 */
public class ButtonCount {
    private long appId;
    private String btnId;
    private long consumerId;
    private String date;
    private String countType;
    private int countValue;

    private long iosViewCount;
    private long androidViewCount;
    private long webViewCount;
    private long otherViewCount;

    private long iosClickCount;
    private long androidClickCount;
    private long webClickCount;
    private long otherClickCount;

    private long iosOpenCount;
    private long androidOpenCount;
    private long webOpenCount;
    private long otherOpenCount;

    private long iosOrderCount;
    private long androidOrderCount;
    private long webOrderCount;
    private long otherOrderCount;

    // 存储的时候转成整型
    private long iosIncome;
    private long androidIncome;
    private long webIncome;
    private long otherIncome;

    private long totalView;
    private long totalClick;
    private long totalOpen;
    private long totalOrder;
    private long totalIncome;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public int getCountValue() {
        return countValue;
    }

    public void setCountValue(int countValue) {
        this.countValue = countValue;
    }

    public long getIosViewCount() {
        return iosViewCount;
    }

    public void setIosViewCount(long iosViewCount) {
        this.iosViewCount = iosViewCount;
    }

    public long getAndroidViewCount() {
        return androidViewCount;
    }

    public void setAndroidViewCount(long androidViewCount) {
        this.androidViewCount = androidViewCount;
    }

    public long getWebViewCount() {
        return webViewCount;
    }

    public void setWebViewCount(long webViewCount) {
        this.webViewCount = webViewCount;
    }

    public long getOtherViewCount() {
        return otherViewCount;
    }

    public void setOtherViewCount(long otherViewCount) {
        this.otherViewCount = otherViewCount;
    }

    public long getIosClickCount() {
        return iosClickCount;
    }

    public void setIosClickCount(long iosClickCount) {
        this.iosClickCount = iosClickCount;
    }

    public long getAndroidClickCount() {
        return androidClickCount;
    }

    public void setAndroidClickCount(long androidClickCount) {
        this.androidClickCount = androidClickCount;
    }

    public long getWebClickCount() {
        return webClickCount;
    }

    public void setWebClickCount(long webClickCount) {
        this.webClickCount = webClickCount;
    }

    public long getOtherClickCount() {
        return otherClickCount;
    }

    public void setOtherClickCount(long otherClickCount) {
        this.otherClickCount = otherClickCount;
    }

    public long getIosOpenCount() {
        return iosOpenCount;
    }

    public void setIosOpenCount(long iosOpenCount) {
        this.iosOpenCount = iosOpenCount;
    }

    public long getAndroidOpenCount() {
        return androidOpenCount;
    }

    public void setAndroidOpenCount(long androidOpenCount) {
        this.androidOpenCount = androidOpenCount;
    }

    public long getWebOpenCount() {
        return webOpenCount;
    }

    public void setWebOpenCount(long webOpenCount) {
        this.webOpenCount = webOpenCount;
    }

    public long getOtherOpenCount() {
        return otherOpenCount;
    }

    public void setOtherOpenCount(long otherOpenCount) {
        this.otherOpenCount = otherOpenCount;
    }

    public long getIosOrderCount() {
        return iosOrderCount;
    }

    public void setIosOrderCount(long iosOrderCount) {
        this.iosOrderCount = iosOrderCount;
    }

    public long getAndroidOrderCount() {
        return androidOrderCount;
    }

    public void setAndroidOrderCount(long androidOrderCount) {
        this.androidOrderCount = androidOrderCount;
    }

    public long getWebOrderCount() {
        return webOrderCount;
    }

    public void setWebOrderCount(long webOrderCount) {
        this.webOrderCount = webOrderCount;
    }

    public long getOtherOrderCount() {
        return otherOrderCount;
    }

    public void setOtherOrderCount(long otherOrderCount) {
        this.otherOrderCount = otherOrderCount;
    }

    public long getIosIncome() {
        return iosIncome;
    }

    public void setIosIncome(long iosIncome) {
        this.iosIncome = iosIncome;
    }

    public long getAndroidIncome() {
        return androidIncome;
    }

    public void setAndroidIncome(long androidIncome) {
        this.androidIncome = androidIncome;
    }

    public long getWebIncome() {
        return webIncome;
    }

    public void setWebIncome(long webIncome) {
        this.webIncome = webIncome;
    }

    public long getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(long otherIncome) {
        this.otherIncome = otherIncome;
    }

    public long getTotalView() {
        return totalView;
    }

    public void setTotalView(long totalView) {
        this.totalView = totalView;
    }

    public long getTotalClick() {
        return totalClick;
    }

    public void setTotalClick(long totalClick) {
        this.totalClick = totalClick;
    }

    public long getTotalOpen() {
        return totalOpen;
    }

    public void setTotalOpen(long totalOpen) {
        this.totalOpen = totalOpen;
    }

    public long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(long totalOrder) {
        this.totalOrder = totalOrder;
    }

    public long getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(long totalIncome) {
        this.totalIncome = totalIncome;
    }
}
