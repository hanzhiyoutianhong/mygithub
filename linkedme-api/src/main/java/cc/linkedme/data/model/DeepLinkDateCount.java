package cc.linkedme.data.model;


/**
 * Created by LinkedME01 on 16/4/17.
 */

public class DeepLinkDateCount {
    private int appId;
    private long deeplinkId;
    private String date;
    private String type;
    private long click;
    private long open;
    private long install;
    
    private long iosClick;
    private long iosOpen;
    private long iosInstall;

    private long adrClick;
    private long adrOpen;
    private long adrInstall;

    private long pcClick;
    private long pcIosScan;
    private long pcAdrScan;
    private long pcIosOpen;
    private long pcAdrOpen;

    private long pcIosInstall;
    private long pcAdrInstall;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public long getDeeplinkId() {
        return deeplinkId;
    }

    public void setDeeplinkId(long deeplinkId) {
        this.deeplinkId = deeplinkId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getClick() {
        return click;
    }

    public void setClick(long click) {
        this.click = click;
    }

    public long getOpen() {
        return open;
    }

    public void setOpen(long open) {
        this.open = open;
    }

    public long getInstall() {
        return install;
    }

    public void setInstall(long install) {
        this.install = install;
    }

    public long getIosClick() {
        return iosClick;
    }

    public void setIosClick(long iosClick) {
        this.iosClick = iosClick;
    }

    public long getIosOpen() {
        return iosOpen;
    }

    public void setIosOpen(long iosOpen) {
        this.iosOpen = iosOpen;
    }

    public long getIosInstall() {
        return iosInstall;
    }

    public void setIosInstall(long iosInstall) {
        this.iosInstall = iosInstall;
    }

    public long getAdrClick() {
        return adrClick;
    }

    public void setAdrClick(long adrClick) {
        this.adrClick = adrClick;
    }

    public long getAdrOpen() {
        return adrOpen;
    }

    public void setAdrOpen(long adrOpen) {
        this.adrOpen = adrOpen;
    }

    public long getAdrInstall() {
        return adrInstall;
    }

    public void setAdrInstall(long adrInstall) {
        this.adrInstall = adrInstall;
    }

    public long getPcClick() {
        return pcClick;
    }

    public void setPcClick(long pcClick) {
        this.pcClick = pcClick;
    }

    public long getPcIosScan() {
        return pcIosScan;
    }

    public void setPcIosScan(long pcIosScan) {
        this.pcIosScan = pcIosScan;
    }

    public long getPcAdrScan() {
        return pcAdrScan;
    }

    public void setPcAdrScan(long pcAdrScan) {
        this.pcAdrScan = pcAdrScan;
    }

    public long getPcIosOpen() {
        return pcIosOpen;
    }

    public void setPcIosOpen(long pcIosOpen) {
        this.pcIosOpen = pcIosOpen;
    }

    public long getPcAdrOpen() {
        return pcAdrOpen;
    }

    public void setPcAdrOpen(long pcAdrOpen) {
        this.pcAdrOpen = pcAdrOpen;
    }

    public long getPcIosInstall() {
        return pcIosInstall;
    }

    public void setPcIosInstall(long pcIosInstall) {
        this.pcIosInstall = pcIosInstall;
    }

    public long getPcAdrInstall() {
        return pcAdrInstall;
    }

    public void setPcAdrInstall(long pcAdrInstall) {
        this.pcAdrInstall = pcAdrInstall;
    }

}
