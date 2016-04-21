package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.util.Util;
import cc.linkedme.commons.util.UuidHelper;
import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
import cc.linkedme.data.model.params.UrlParams;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import org.springframework.dao.DataAccessException;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.model.DeepLink;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public class DeepLinkDaoImpl extends BaseDao implements DeepLinkDao {
    private static final String ADD_DEEPLINK = "ADD_DEEPLINK";
    private static final String GET_DEEPLINKS = "GET_DEEPLINKS";
    private static final String GET_DEEPLINK_INFO = "GET_DEEPLINK_INFO";
    private static final String DELETE_DEEPLINK = "DELETE_DEEPLINK";
    private static final String GET_URL_INFO = "GET_URL_INFO";
    private static final String UPDATE_URL_INFO = "UPDATE_URL_INFO";

    public int addDeepLink(DeepLink deepLink) {
        int result = 0;
        if (deepLink == null) {
            ApiLogger.error("DeepLinkDaoImpl.addDeepLink Deeplink is null, add failed");
        }

        long deeplink_id = deepLink.getDeeplinkId();
        String deeplink_md5 = deepLink.getDeeplinkMd5();
        String linkedme_key = deepLink.getLinkedmeKey();
        long appid = deepLink.getAppId();
        long identity_id = deepLink.getIdentityId();
        String create_time = deepLink.getCreateTime();
        String tags = deepLink.getTags();
        String alias = deepLink.getAlias();
        String channel = deepLink.getChannel();
        String feature = deepLink.getFeature();
        String stage = deepLink.getStage();
        String campaign = deepLink.getCampaign();
        String params = deepLink.getParams();
        String source = deepLink.getSource();
        String sdk_version = deepLink.getSdkVersion();

        String link_label = deepLink.getLink_label();
        boolean ios_use_default = deepLink.isIos_use_default();
        String ios_custom_url = deepLink.getIos_custom_url();
        boolean android_use_default = deepLink.isAndroid_use_default();
        String android_custom_url = deepLink.getAndroid_custom_url();
        boolean desktop_use_default = deepLink.isDesktop_use_default();
        String desktop_custom_url = deepLink.getDesktop_custom_url();

        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", ADD_DEEPLINK, appid, new Date());

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(),
                    new Object[] {deeplink_id, deeplink_md5, linkedme_key, identity_id, appid, create_time, tags, alias, channel, feature,
                            stage, campaign, params, source, sdk_version, link_label, ios_use_default, ios_custom_url, android_use_default,
                            android_custom_url, desktop_use_default, desktop_custom_url});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.info(new StringBuilder(128).append("Duplicate insert deepLink, id=").append(deeplink_id));
                throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP,
                        "duplicate insert deepLink table, id=" + deepLink.getDeeplinkId());
            } else {
                throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
            }
        }
        return result;
    }

    public DeepLink getDeepLinkInfo(long deepLinkId, long appid) {
        Date date = UuidHelper.getDateFromId(deepLinkId); // 根据deepLinkId获取日期
        DeepLink dp = new DeepLink();
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", GET_DEEPLINK_INFO, appid, date);
        try {

            tableChannel.getJdbcTemplate().query(tableChannel.getSql(), new Object[]{deepLinkId, appid}, new RowMapper() {
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    dp.setDeeplinkId(resultSet.getBigDecimal("deeplink_id").longValue());
                    dp.setCreateTime(resultSet.getString("create_time"));
                    dp.setTags(resultSet.getString("tags"));
                    dp.setAlias(resultSet.getString("alias"));
                    dp.setChannel(resultSet.getString("channel"));
                    dp.setFeature(resultSet.getString("feature"));
                    dp.setStage(resultSet.getString("stage"));
                    dp.setCampaign(resultSet.getString("campaign"));
                    dp.setParams(resultSet.getString("params"));
                    dp.setSource(resultSet.getString("source"));
                    dp.setIos_use_default(resultSet.getBoolean("ios_use_default"));
                    dp.setIos_custom_url(resultSet.getString("ios_custom_url"));
                    dp.setAndroid_use_default(resultSet.getBoolean("android_use_default"));
                    dp.setAndroid_custom_url(resultSet.getString("android_custom_url"));
                    dp.setDesktop_use_default(resultSet.getBoolean("desktop_use_default"));
                    dp.setDesktop_custom_url(resultSet.getString("desktop_custom_url"));
                    return null;
                }
            });
        }catch (Exception e) {
            ApiLogger.error("db error", e);
        }
        if (dp.getDeeplinkId() == 0) {
            return null;
        }
        return dp;
    }

    public DeepLink getUrlInfo(long deepLinkId, long appid) {
        Date date = UuidHelper.getDateFromId(deepLinkId); // 根据deepLinkId获取日期

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date onlineDate = sdf.parse("2016-04-01");
            Date currentDate = new Date();

            if (date.after(currentDate) || date.before(onlineDate)) {
                throw new LMException(LMExceptionFactor.LM_ILLEGAL_REQUEST, "deep link id does not exist!");
            }
        } catch (ParseException e) {
            ApiLogger.warn("SummaryService.getDeepLinks parse date failed", e);
            throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAM_VALUE, "date param is illegal");
        }


        final List<DeepLink> deepLinks = new ArrayList<DeepLink>();
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", GET_URL_INFO, appid, date);
        tableChannel.getJdbcTemplate().query(tableChannel.getSql(), new Object[] {deepLinkId, appid}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                DeepLink dp = new DeepLink();
                dp.setLink_label(resultSet.getString("link_label"));
                dp.setIos_use_default(resultSet.getBoolean("ios_use_default"));
                dp.setIos_custom_url(resultSet.getString("ios_custom_url"));
                dp.setAndroid_use_default(resultSet.getBoolean("android_use_default"));
                dp.setAndroid_custom_url(resultSet.getString("android_custom_url"));
                dp.setDesktop_use_default(resultSet.getBoolean("desktop_use_default"));
                dp.setDesktop_custom_url(resultSet.getString("desktop_custom_url"));
                dp.setFeature(resultSet.getString("feature"));
                dp.setCampaign(resultSet.getString("campaign"));
                dp.setStage(resultSet.getString("stage"));
                dp.setChannel(resultSet.getString("channel"));
                dp.setTags(resultSet.getString("tags"));
                dp.setSource(resultSet.getString("source"));
                dp.setParams(resultSet.getString("params"));

                deepLinks.add(dp);
                return null;
            }
        });
        if (deepLinks.size() > 0) {
            return deepLinks.get(0);
        }
        return null;
    }

    public List<DeepLink> getDeepLinks(long appid, String start_date, String end_date, String feature, String campaign, String stage,
            String channel, String tag, boolean unique) {
        Date date = Util.timeStrToDate(start_date);
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", GET_DEEPLINKS, appid, date);
        String sql = tableChannel.getSql();
        String condition = "";
        List<String> paramList = new ArrayList<String>();
        paramList.add(String.valueOf(appid));
        if (!Strings.isNullOrEmpty(start_date)) {
            condition += "and create_time >= ? ";
            paramList.add(start_date);
        }
        if (!Strings.isNullOrEmpty(end_date)) {
            condition += "and create_time <= ? ";
            paramList.add(end_date);
        }
        if (!Strings.isNullOrEmpty(feature)) {
            condition += "and feature like '%' ? '%' ";
            paramList.add(feature);
        }
        if (!Strings.isNullOrEmpty(campaign)) {
            condition += "and campaign like '%' ? '%' ";
            paramList.add(campaign);
        }
        if (!Strings.isNullOrEmpty(stage)) {
            condition += "and stage like '%' ? '%' ";
            paramList.add(stage);
        }
        if (!Strings.isNullOrEmpty(channel)) {
            condition += "and channel like '%' ? '%' ";
            paramList.add(channel);
        }
        if (!Strings.isNullOrEmpty(tag)) {
            condition += "and tags like '%' ? '%' ";
            paramList.add(tag);
        }
        if (condition.length() > 0) {
            sql = sql + condition;
        }

        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<DeepLink> deepLinks = new ArrayList<DeepLink>();
        jdbcTemplate.query(sql, paramList.toArray(), new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                DeepLink dp = new DeepLink();
                dp.setAppId(appid);
                dp.setDeeplinkId(resultSet.getBigDecimal("deeplink_id").longValue());
                dp.setCreateTime(resultSet.getString("create_time"));
                dp.setTags(resultSet.getString("tags"));
                dp.setAlias(resultSet.getString("alias"));
                dp.setChannel(resultSet.getString("channel"));
                dp.setFeature(resultSet.getString("feature"));
                dp.setStage(resultSet.getString("stage"));
                dp.setCampaign(resultSet.getString("campaign"));
                dp.setSource(resultSet.getString("source"));
                deepLinks.add(dp);
                return null;
            }
        });
        return deepLinks;

    }

    public boolean deleteDeepLink(long deepLinkId, long appId) {
        int result = 0;
        Date date = UuidHelper.getDateFromId(deepLinkId);
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", DELETE_DEEPLINK, appId, date);
        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {deepLinkId});
        } catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return result > 0;
    }

    public boolean updateUrlInfo(UrlParams urlParams) {
        long deepLinkId = urlParams.deeplink_id;
        long appId = urlParams.app_id;
        Date date = UuidHelper.getDateFromId(deepLinkId);
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", UPDATE_URL_INFO, appId, date);
        int result = 0;

        long userid = urlParams.user_id;
        boolean ios_use_default = urlParams.ios_use_default;
        String ios_custom_url = urlParams.ios_custom_url;
        boolean android_use_default = urlParams.android_use_default;
        String android_custom_url = urlParams.android_custom_url;
        boolean desktop_use_default = urlParams.desktop_use_default;
        String desktop_custom_url = urlParams.desktop_custom_url;
        String feature = "";
        for (int i = 0; i < urlParams.feature.length - 1; i++)
            feature = feature + urlParams.feature[i] + ",";
        feature = feature + urlParams.feature[urlParams.feature.length - 1];

        String campaign = "";
        for (int i = 0; i < urlParams.campaign.length - 1; i++)
            campaign = campaign + urlParams.campaign[i] + ",";
        campaign = campaign + urlParams.campaign[urlParams.campaign.length - 1];

        String stage = "";
        for (int i = 0; i < urlParams.stage.length - 1; i++)
            stage = stage + urlParams.stage[i] + ",";
        stage = stage + urlParams.stage[urlParams.stage.length - 1];

        String channel = "";
        for (int i = 0; i < urlParams.channel.length - 1; i++)
            channel = channel + urlParams.channel[i] + ",";
        channel = channel + urlParams.channel[urlParams.channel.length - 1];

        String tags = "";
        for (int i = 0; i < urlParams.tags.length - 1; i++)
            tags = tags + urlParams.tags[i] + ",";
        tags = tags + urlParams.tags[urlParams.tags.length - 1];

        String source = urlParams.source;
        String params = urlParams.params.toString();


        result +=
                tableChannel.getJdbcTemplate()
                        .update(tableChannel.getSql(),
                                new Object[] {appId, ios_use_default, ios_custom_url, android_use_default, android_custom_url,
                                        desktop_use_default, desktop_custom_url, feature, campaign, stage, channel, tags, source, params,
                                        deepLinkId, appId});
        if (result == 1) return true;
        return false;
    }
}
