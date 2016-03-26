package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.commons.util.Util;
import cc.linkedme.dao.sdkapi.DeepLinkDao;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.SummaryDeepLinkParams;
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
    private static final String GET_DEEPLINK_INFO = "GET_DEEPLINKS_INFO";

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
        String source = deepLink.getSource();
        String sdk_version = deepLink.getSdkVersion();
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", ADD_DEEPLINK, appid, new Date());

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {deeplink_id, deeplink_md5, linkedme_key,
                    identity_id, appid, create_time, tags, alias, channel, feature, stage, campaign, source, sdk_version});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.info(new StringBuilder(128).append("Duplicate insert deepLink, id=").append(deeplink_id));
            } else {
                throw e;
            }
        }
        return result;
    }

    public DeepLink getDeepLinkInfo(long deepLinkId, long appid) {
        Date date = null;   //根据deepLinkId获取日期
        final List<DeepLink> deepLinks = new ArrayList<DeepLink>();
        TableChannel tableChannel = tableContainer.getTableChannel("deeplink", GET_DEEPLINKS, appid, date);
        tableChannel.getJdbcTemplate().query(tableChannel.getSql(), new Object[] {deepLinkId, appid}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                DeepLink dp = new DeepLink();
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
        if(deepLinks.size() > 0) {
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
        if (start_date != null) {
            condition += "and create_time >= ? ";
            paramList.add(start_date);
        }
        if (end_date != null) {
            condition += "and create_time <= ? ";
            paramList.add(end_date);
        }
        if (feature != null) {
            condition += "and feature like '%' ? '%' ";
            paramList.add(feature);
        }
        if (campaign != null) {
            condition += "and campaign like '%' ? '%' ";
            paramList.add(campaign);
        }
        if (stage != null) {
            condition += "and stage like '%' ? '%' ";
            paramList.add(stage);
        }
        if (channel != null) {
            condition += "and channel like '%' ? '%' ";
            paramList.add(channel);
        }
        if (tag != null) {
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
}
