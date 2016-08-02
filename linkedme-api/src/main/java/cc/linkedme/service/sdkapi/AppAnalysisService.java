package cc.linkedme.service.sdkapi;

/**
 * Created by LinkedME07 on 16/7/27.
 */
public interface AppAnalysisService {

    String addAppBundle(String api, String company);

    String getChangedApps(String company, String date);

    String getApps(String company);

    String updateStatus(String appId, String company, String status);

    String count(String company, String startDate, String endDate);

    String count(String company, int interval);

}
