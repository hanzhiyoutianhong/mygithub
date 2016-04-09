package cc.linkedme.data.model.params;

/**
 * Created by LinkedME01 on 16/3/20.
 */
public class SummaryDeepLinkParams {

    public long appid;
    public String startDate;
    public String endDate;
    public String feature;
    public String campaign;
    public String stage;
    public String channel;
    public String tags;
    public String source;
    public boolean unique;
    public int returnNumber;
    public int skipNumber;
    public String orderby;
    public String deepLinkUrl;
    public long deepLinkId;

    public SummaryDeepLinkParams() {}

    public SummaryDeepLinkParams(long appid, String startDate, String endDate, String feature, String campaign, String stage,
            String channel, String tags, String source, boolean unique, int returnNumber, int skipNumber, String orderby) {
        this.appid = appid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.feature = feature;
        this.campaign = campaign;
        this.stage = stage;
        this.channel = channel;
        this.tags = tags;
        this.source = source;
        this.unique = unique;
        this.returnNumber = returnNumber;
        this.skipNumber = skipNumber;
        this.orderby = orderby;
    }
}
