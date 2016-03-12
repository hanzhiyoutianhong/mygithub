package cc.linkedme.dao.impl;

import cc.linkedme.dao.LMLinkDao;
import cc.linkedme.model.LMLinkEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by qipo on 15/9/1.
 */
public class LMLinkDaoImpl implements LMLinkDao {

    private static final Logger logger = LoggerFactory.getLogger(LMLinkDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_LINK =
            "INSERT INTO link(identity_id, app_key, type, tags, channel, " + "feature, stage, alias, sdk, data, "
                    + "source, deeplinkpath, link_identifier, link_click_id, clicks, " + "install, reject_install, open, weibo, wechat, "
                    + "retry_number, ip, timestamp)" + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String QUERY_ALL_LINK = "SELECT * FROM link";
    public static final String QUERY_A_LINK = "SELECT * FROM link WHERE link_click_id=?";
    public static final String UPDATE_INSTALL_LINK = "UPDATE link SET install = install + 1, clicks = clicks + 1 WHERE link_click_id=?";

    public static final String UPDATE_OPEN_LINK = "UPDATE link SET open = open + 1, clicks = clicks + 1 WHERE link_click_id=?";
    public static final String UPDATE_CLICK_LINK = "UPDATE link SET clicks = clicks + 1 WHERE link_click_id=?";
    public static final String UPDATE_WEIBO_LINK = "UPDATE link SET weibo = weibo + 1 WHERE link_click_id=?";
    public static final String UPDATE_WECHAT_LINK = "UPDATE link SET wechat = wechat + 1 WHERE link_click_id=?";
    public static final String UPDATE_REJECT_INSTALL_LINK = "UPDATE link SET reject_install = reject_install + 1 WHERE link_click_id=?";

    public void addLink(LMLinkEntity le) {
        Object[] values = {le.getIdentityId(), le.getAppKey(), le.getType(), le.getTags(), le.getChannel(), le.getFeature(), le.getStage(),
                le.getAlias(), le.getSdk(), le.getData(), le.getSource(), le.getDeeplinkpath(), le.getLinkIdentifier(), le.getLinkClickId(),
                le.getClicks(), le.getInstall(), le.getRejectInstall(), le.getOpen(), le.getWeibo(), le.getWechat(), le.getRetryNumber(),
                le.getIp(), le.getTimestamp()};
        jdbcTemplate.update(INSERT_LINK, values);
    }

    public LMLinkEntity getOneLink(String linkClickId) {
        Object[] values = {linkClickId};
        List<LMLinkEntity> allRowList = jdbcTemplate.query(QUERY_A_LINK, values, new BeanPropertyRowMapper(LMLinkEntity.class));
        if (allRowList.size() == 0) {
            return null;
        } else {
            return allRowList.get(0);
        }
    }

    public void updateInstallLink(String linkClickId) {
        Object[] values = {linkClickId};
        jdbcTemplate.update(UPDATE_INSTALL_LINK, values);
    }

    public void updateOpenLink(String linkClickId) {
        Object[] values = {linkClickId};
        jdbcTemplate.update(UPDATE_OPEN_LINK, values);
    }


    public void updateClickLink(String linkClickId) {
        Object[] values = {linkClickId};
        jdbcTemplate.update(UPDATE_CLICK_LINK, values);

    }

    public void updateWeChatLink(String linkClickId) {
        Object[] values = {linkClickId};
        jdbcTemplate.update(UPDATE_WECHAT_LINK, values);
    }

    public void updateWeiboLink(String linkClickId) {
        Object[] values = {linkClickId};
        jdbcTemplate.update(UPDATE_WEIBO_LINK, values);
    }

    public void updateRejectInstallLink(String linkClickId) {
        Object[] values = {linkClickId};
        jdbcTemplate.update(UPDATE_REJECT_INSTALL_LINK, values);
    }

    public List<LMLinkEntity> getAllLink() {
        List<LMLinkEntity> allRowsList = jdbcTemplate.query(QUERY_ALL_LINK, new BeanPropertyRowMapper(LMLinkEntity.class));
        return allRowsList;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
