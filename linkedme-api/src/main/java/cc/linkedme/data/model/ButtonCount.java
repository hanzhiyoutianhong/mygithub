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

    private int iosViewCount;
    private int androidViewCount;
    private int webViewCount;
    private int otherViewCount;

    private int iosClickCount;
    private int androidClickCount;
    private int webClickCount;
    private int otherClickCount;

    private int iosOpenCount;
    private int androidOpenCount;
    private int webOpenCount;
    private int otherOpenCount;

    private int iosOrderCount;
    private int androidOrderCount;
    private int webOrderCount;
    private int otherOrderCount;

    //存储的时候转成整型
    private int iosIncome;
    private int androidIncome;
    private int webIncome;
    private int otherIncome;

    private int totalView;
    private int totalClick;
    private int totalOpen;
    private int totalOrder;
    private int totalIncome;

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

    public int getIosViewCount() {
        return iosViewCount;
    }

    public void setIosViewCount(int iosViewCount) {
        this.iosViewCount = iosViewCount;
    }

    public int getAndroidViewCount() {
        return androidViewCount;
    }

    public void setAndroidViewCount(int androidViewCount) {
        this.androidViewCount = androidViewCount;
    }

    public int getWebViewCount() {
        return webViewCount;
    }

    public void setWebViewCount(int webViewCount) {
        this.webViewCount = webViewCount;
    }

    public int getOtherViewCount() {
        return otherViewCount;
    }

    public void setOtherViewCount(int otherViewCount) {
        this.otherViewCount = otherViewCount;
    }

    public int getIosClickCount() {
        return iosClickCount;
    }

    public void setIosClickCount(int iosClickCount) {
        this.iosClickCount = iosClickCount;
    }

    public int getAndroidClickCount() {
        return androidClickCount;
    }

    public void setAndroidClickCount(int androidClickCount) {
        this.androidClickCount = androidClickCount;
    }

    public int getWebClickCount() {
        return webClickCount;
    }

    public void setWebClickCount(int webClickCount) {
        this.webClickCount = webClickCount;
    }

    public int getOtherClickCount() {
        return otherClickCount;
    }

    public void setOtherClickCount(int otherClickCount) {
        this.otherClickCount = otherClickCount;
    }

    public int getIosOpenCount() {
        return iosOpenCount;
    }

    public void setIosOpenCount(int iosOpenCount) {
        this.iosOpenCount = iosOpenCount;
    }

    public int getAndroidOpenCount() {
        return androidOpenCount;
    }

    public void setAndroidOpenCount(int androidOpenCount) {
        this.androidOpenCount = androidOpenCount;
    }

    public int getWebOpenCount() {
        return webOpenCount;
    }

    public void setWebOpenCount(int webOpenCount) {
        this.webOpenCount = webOpenCount;
    }

    public int getOtherOpenCount() {
        return otherOpenCount;
    }

    public void setOtherOpenCount(int otherOpenCount) {
        this.otherOpenCount = otherOpenCount;
    }

    public int getIosOrderCount() {
        return iosOrderCount;
    }

    public void setIosOrderCount(int iosOrderCount) {
        this.iosOrderCount = iosOrderCount;
    }

    public int getAndroidOrderCount() {
        return androidOrderCount;
    }

    public void setAndroidOrderCount(int androidOrderCount) {
        this.androidOrderCount = androidOrderCount;
    }

    public int getWebOrderCount() {
        return webOrderCount;
    }

    public void setWebOrderCount(int webOrderCount) {
        this.webOrderCount = webOrderCount;
    }

    public int getOtherOrderCount() {
        return otherOrderCount;
    }

    public void setOtherOrderCount(int otherOrderCount) {
        this.otherOrderCount = otherOrderCount;
    }

    public int getIosIncome() {
        return iosIncome;
    }

    public void setIosIncome(int iosIncome) {
        this.iosIncome = iosIncome;
    }

    public int getAndroidIncome() {
        return androidIncome;
    }

    public void setAndroidIncome(int androidIncome) {
        this.androidIncome = androidIncome;
    }

    public int getWebIncome() {
        return webIncome;
    }

    public void setWebIncome(int webIncome) {
        this.webIncome = webIncome;
    }

    public int getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(int otherIncome) {
        this.otherIncome = otherIncome;
    }

    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
        this.totalView = totalView;
    }

    public int getTotalClick() {
        return totalClick;
    }

    public void setTotalClick(int totalClick) {
        this.totalClick = totalClick;
    }

    public int getTotalOpen() {
        return totalOpen;
    }

    public void setTotalOpen(int totalOpen) {
        this.totalOpen = totalOpen;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }
}
