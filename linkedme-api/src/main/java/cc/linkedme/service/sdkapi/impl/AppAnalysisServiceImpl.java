package cc.linkedme.service.sdkapi.impl;

import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.sdkapi.AppAnalysisDao;
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.AppAnalysisParams;
import cc.linkedme.service.sdkapi.AppAnalysisService;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LinkedME07 on 16/7/27.
 */
public class AppAnalysisServiceImpl implements AppAnalysisService {

    private static final String GET_APPS = "GET_APPS";
    private static final String OFFLINE_APPS = "OFFLINE_APPS";
    private static final String GET_INC_APPS = "GET_INC_APPS";
    private static final String GET_DES_APPS = "GET_DES_APPS";
    private static final String START_RECORD_DATE = "2016-07-31";
    private static final String ITUNES_APPLE_BUNDLES = "https://itunes.apple.com/lookup?bundleId=";

    @Resource
    private AppAnalysisDao appAnalysisDao;


    @Override
    public String addAppBundle(String api, String company) {

        String result = Util.httpGet(api);
        if (result == null) return null;

        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONObject applinks = jsonObject.getJSONObject("applinks");
        JSONArray details = applinks.getJSONArray("details");

        List<String> oldApps = appAnalysisDao.getAppIds(new Object[] {company}, GET_APPS);

        for (int i = 0; i < details.size(); i++) {
            String appId = details.getJSONObject(i).getString("appID");
            appId = appId.substring(appId.indexOf('.') + 1).replace(" ", "");

            if (appId.length() > 100) {
                appId = appId.substring(0, 100);
            }

            if (oldApps.contains(appId)) {
                continue;
            }

            String res = Util.httpGet(ITUNES_APPLE_BUNDLES + appId);
            if (Strings.isNullOrEmpty(res)) {
                continue;
            }
            JSONObject jsonRes = JSONObject.fromObject(res);
            int resultCount = jsonRes.getInt("resultCount");

            String appName = null;
            String lastUpdateTime = null;
            String appIcon = null;
            int isOnline = 0;
            String genres = null;

            if (resultCount > 0) {
                JSONObject data = jsonRes.getJSONArray("results").getJSONObject(0);
                appName = data.getString("trackCensoredName");
                lastUpdateTime = data.getString("currentVersionReleaseDate");
                appIcon = data.getString("artworkUrl100");
                lastUpdateTime = lastUpdateTime.replace("T", " ").replace("Z", "");
                genres = data.getJSONArray("genres").get(0).toString();
                isOnline = 1;
            }

            int rs = appAnalysisDao.addAppBundle(appId, appName, appIcon, genres, company, lastUpdateTime, isOnline);
            if (rs > 0) {
                oldApps.remove(appId);
            }
        }

        for (int i = 0; i < oldApps.size(); i++) {
            appAnalysisDao.switchApp(oldApps.get(i), company, OFFLINE_APPS);
        }
        return "ok";
    }

    public String getChangedApps(String company, String date) {
        List incApps = appAnalysisDao.getBundleIdAndUserInfo(new Object[] {company, date}, GET_INC_APPS);
        List desApps = appAnalysisDao.getBundleIdAndUserInfo(new Object[] {company, date}, GET_DES_APPS);
        Map<String, List<String>> changedApps = new HashMap<>();

        if (incApps.size() > 0) {
            changedApps.put("inc_apps", incApps);
        } else {
            changedApps.put("inc_apps", new ArrayList<>());
        }

        if (desApps.size() > 0) {
            changedApps.put("des_apps", desApps);
        } else {
            changedApps.put("des_apps", new ArrayList<>());
        }
        return JSONObject.fromObject(changedApps).toString();

    }

    public String getApps(String company) {

        List<AppAnalysisParams> appAnalysisList = appAnalysisDao.getApps(company);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        if (appAnalysisList.size() == 0) {
            jsonObject.put("count", 0);
        } else {
            jsonObject.put("count", appAnalysisList.size());
        }

        for (AppAnalysisParams app : appAnalysisList) {
            JSONObject json = new JSONObject();
            json.put("app_id", app.getAppId());
            json.put("status", app.getStatus());
            json.put("app_name", app.getAppName() == null ? "" : app.getAppName());
            json.put("last_update_time", app.getLastUpdateTime() == null ? "" : app.getLastUpdateTime());
            json.put("app_icon", app.getAppIcon() == null ? "" : app.getAppIcon());
            json.put("is_online", app.getIsOnline());
            jsonArray.add(json);
        }

        jsonObject.put("data", jsonArray);

        return jsonObject.toString();
    }

    public String count(String company, int interval) {

        Date startRecord = Util.timeStrToDate(START_RECORD_DATE);

        if (interval <= 0) {
            interval = 7;
        }

        List<String> days = Util.getLastDays(interval);

        JSONArray jsonArray = new JSONArray();

        for (String date : days) {
            if (Util.timeStrToDate(date).before(startRecord)) {
                continue;
            }
            JSONObject json = resultToJson(company, date, date);
            json.put("date", date);
            jsonArray.add(json);
        }
        return jsonArray.toString();
    }

    private JSONObject resultToJson(String company, String startDate, String endDate) {

        List<AppAnalysisParams> appAnalysisList = appAnalysisDao.getAppsWithSDK(company, startDate, endDate);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (AppAnalysisParams app : appAnalysisList) {
            JSONObject json = new JSONObject();
            json.put("app_name", app.getAppName() == null ? "" : app.getAppName());
            json.put("genres", app.getGenres() == null ? "" : app.getGenres());
            jsonArray.add(json);
        }

        jsonObject.put("app_genres", jsonArray);

        int bundleIdCount = appAnalysisDao.count(company, startDate, endDate, "COUNT_BUNDLE_ID");
        jsonObject.put("bundle_count", bundleIdCount);

        int appIsOnlineCount = appAnalysisDao.count(company, startDate, endDate, "COUNT_APP_IS_ONLINE");
        jsonObject.put("online_count", appIsOnlineCount);

        int appWithSDKCount = appAnalysisDao.count(company, startDate, endDate, "COUNT_WITH_SDK");
        jsonObject.put("sdk_count", appWithSDKCount);

        return jsonObject;
    }


    public String count(String company, String startDate, String endDate) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (Strings.isNullOrEmpty(startDate)) {
            Date now = new Date();
            startDate = df.format(now);
        }

        if (Strings.isNullOrEmpty(endDate)) {
            Date now = new Date();
            startDate = df.format(now);
        }
        return resultToJson(company, startDate, endDate).toString();
    }

    public String updateStatus(String appId, String company, String status) {
        int result = appAnalysisDao.updateStatus(appId, company, status);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", "200");
        } else {
            jsonObject.put("code", "500");
        }
        return jsonObject.toString();
    }

    public static void main( String[] args ) {
        UserInfo userInfo = new UserInfo();
        userInfo.setCompany("");
    }
}
