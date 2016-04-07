package cc.linkedme.commons.util;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Constants {
    // profiling pull aggregator getFriendTimeLine:
    // 1. get following list time, 2. multi get vectorcache time
    // 3. sort time, 4. cat content cache time
    public static final String DEEPLINK_HTTP_PREFIX = "http://lkme.cc/";
    public static final String DEEPLINK_HTTPS_PREFIX = "http://192.168.0.104:8888/";
//    public static final String DEEPLINK_HTTPS_PREFIX_LOCAL = "https://lkme.cc/l/";
    public static final boolean enableProfiling = true;
    public static boolean enablePageCache = false;
    public static final boolean enableDeletedData = false;

    public static AtomicLong followingCount = new AtomicLong(0);
    public static AtomicLong followingTime = new AtomicLong(0);
    public static AtomicLong vectorTime = new AtomicLong(0);

    public static AtomicLong contentTime = new AtomicLong(0);

    public static AtomicLong test1Time = new AtomicLong(0);
    public static AtomicLong test2Time = new AtomicLong(0);

    public static AtomicLong sortTimeForTest = new AtomicLong(0);
    public static AtomicLong vectorGetTimeForTest = new AtomicLong(0);

    /**
     * 分配的源id，api为21
     */
    public static int SRC_API_ID = 21;
    public static int SRC_STATUS_ID = 211;
    public static int SRC_COMMENT_ID = 212;
    public static int SRC_DM_ID = 213;

    public static int TIMELINE_SIZE = 2000;
    public static int MAX_TIMELINE_SIZE = 5000;

    /**
     * 最大关注数
     */
    public static int MAX_FRIEND_CACHE_SIZE = 5000;
    public static int MAX_DM_WITH_UID_CACHE_SIZE = 2000;
    public static int MAX_FRIEND_COUNT = 2000;
    public static int MAX_FRIEND_VIP_COUNT = 5000;
    /**
     * 数据集最大返回长度,vector也是用200作为长度尺寸
     */
    public static int MAX_RETURN_COUNT = 200;
    public static int DEFAULT_RETURN_COUNT = 20;

    public static int MAX_FAVORITE_RETURN_COUNT = 2000;

    /**
     * check followers 时，最大的 friends 列表长度
     */
    public static int MAX_CHECKFOLLOERS_FRIEND_COUNT = 500;

    /**
     * 长尾数据一批最大从数据库获取的条目数
     */
    public static int MAX_TAIL_RETURN_COUNT = 2000;

    public static int MAX_VECTOR_SIZE = 200;
    public static int MAX_SPEC_VECTOR_SIZE = 200;
    public static int MAX_TAIL_VECTOR_SIZE = 2000;
    public static int MAX_SGROUP_VECTOR_SIZE = 50; // 私密群微博缓存大小
    public static int MAX_MVD_VECTOR_SIZE = 50;
    public static int MAX_MVL_VECTOR_SIZE = 200;

    public static int MAX_PVD_VECTOR_SIZE = 200;
    public static int MAX_PAGE_GROUP_VECTOR_SIZE = 200;

    // 内部实现变量
    public static int MAX_MULTIGET_SIZE = 2000;
    // fix bug:只取10个月的数据不够，实际应该取更多，先改为60，支持5年的数据，后续应该优化整个长尾方案 fishermen 2011.6.27
    public static int SICOUNT = 60;
    public static int VECTOR_THRESHOLD = 10;
    public static int MAX_BATCH_FETCH_SIZE = 50; // 一批的最大处理数
    public static int DVECTOR_BATCH_COUNT = 50; // 一批的最大处理数
    public static final int META_VECTOR_TTL = 5;
    public static final int MVD_META_VECTOR_TTL = 15;
    public static final int MAX_META_VECTOR_RETURN_COUNT = 2000;

    public static final String KEY_SEPERATOR = ".";
    public static final String ALL_HASH_FIELDS = "all_hash_fields";

    // cache expire time settings
    // 发现 vector cache 由2天增为4天，2天时间太短 fish 2010.12.31
    public static Date EXPTIME_VECTOR = new Date(1000l * 4 * 24 * 60 * 60);
    public static Date EXPTIME_HOUR_VECTOR = new Date(1000l * 60 * 60);
    public static Date EXPTIME_VECTOR_MENTION = new Date(1000l * 4 * 24 * 60 * 60);
    public static Date EXPTIME_VECTOR_DM = new Date(1000l * 4 * 24 * 60 * 60);
    public static Date EXPTIME_VECTOR_COMMENT = new Date(1000l * 4 * 24 * 60 * 60);
    public static Date EXPTIME_CONTENT = new Date(1000l * 15 * 24 * 60 * 60);
    public static Date EXPTIME_PAGE = new Date(1000l * 60 * 5);
    public static Date EXPTIME_USER = new Date(1000l * 4 * 24 * 60 * 60);
    public static Date EXPTIME_MAPPING = new Date(1000l * 15 * 24 * 60 * 60);
    public static Date EXPTIME_ATTENTION = new Date(1000l * 15 * 24 * 60 * 60);
    public static Date EXPTIME_STATUS_CMT = new Date(1000l * 2 * 24 * 60 * 60);
    public static Date EXPTIME_STATUS_MEMBER_CMT = new Date(1000l * 2 * 24 * 60 * 60);
    public static Date EXPTIME_VECTOR_ATTITUDE = new Date(1000l * 4 * 24 * 60 * 60);
    public static Date EXPTIME_STATUS_ATTITUDE = new Date(1000l * 2 * 24 * 60 * 60);
    public static Date EXPTIME_VECTOR_MVD = new Date(1000l * 15 * 24 * 60 * 60);


    // 脏数据缓存过期时间 1 分钟
    public static Date EXPTIME_VECTOR_DIRTY = new Date(1000l * 60);

    // Meta vector content missed expire time is 1 hour.
    public static Date EXPTIME_META_VECTOR_CONTENT_MISSED = new Date(1000l * 60 * 60);

    // Meta vector userType missed expire time is 1 hour.
    public static Date EXPTIME_META_VECTOR_USERTYPE_MISSED = new Date(1000l * 60 * 60);

    // 微博timeline multi DB查询超时
    public static long TIMEOUT_MULTI_USER_TIMELINE = 600;
    // memcache read timeout, msec
    public static long TIMEOUT_MULTIGET_VECTOR = 5000;
    public static long TIMEOUT_MULTIGET_CONTENT = 5000;
    public static long TIMEOUT_MULTIGET_COMMON = 5000;

    public static long TIMEOUT_TAIL_GET = 1000;

    public static long DB_TIMEOUT_CONTENT = 500;
    public static long DB_TIMEOUT_COMMON_QUERY = 1000;

    public static long TIMEOUT_SLICE_CONTENT_GET = 100;

    public static long TIMEOUT_STATUS_CONTENT_TASK = 800;
    public static long TIMEOUT_STATUS_LONGTEXT_CONTENT_TASK = 300;

    public static long SIMPLE_TIMEOUT_MULTIGET_USER = 2000;
    public static long SIMPLE_TIMEOUT_MULTIGET_CONTENT = 500;
    public static long SIMPLE_TIMEOUT_MULTIGET_ATTACHMENT = 1000;
    public static long SIMPLE_TIMEOUT_MULTIGET_ALLSTATUS = 1000;

    public static long TIMEOUT_GET_STATUS_COUTER = 500;

    public static long TIMEOUT_GET_STATUS_READ_COUNT = 100;

    public static long TIMEOUT_GET_USER_LAST_STATUS = 500;
    public static long TIMEOUT_GET_USER_COUTER = 500;
    public static long TIMEOUT_GET_USER_SESSION = 500;
    public static long TIMEOUT_GET_USER_ONLINE_STATES = 200;

    // 获取最近一条赞数据的超时
    public static long TIMEOUT_GET_LATEST_LIKE = 200;

    // test var
    public static long MAX_UID = 20000;
    public static long MIN_UID = 10000;

    // try cas times
    public static int CAS_TIME = 2;
    public static int MAX_CAS_TIME = 3;

    /**
     * 是否返回twitter兼容字段，这些字段在微博中无业务意义
     */
    public static boolean twitterCompatibleOutput = false;

    // return type
    public static String RETURN_TYPE_JSON = "json";
    public static String RETURN_TYPE_XML = "xml";

    public static int CURSOR_NO_RECORD = 0;

    public static int OP_DB_TIMEOUT = 100;

    /**
     * 用户、关注等慢处理的阀值
     */
    public static int OP_MYSQL_MEMC_TIMEOUT = 200;

    public static int OP_REDIS_TIMEOUT = 20;

    public static int OP_SOCIAL_GRAPH = 400;

    public static int OP_CACHE_TIMEOUT = 200;

    // 消息处理超时时间
    public static int OP_MQPROC_TIMEOUT = 100;

    // FEED超时时间
    public static int FEED_TIMEOUT = 300;

    // 用户微博列表超时时间
    public static int USER_TIMELINE_TIMEOUT = 1000;

    // 取置顶微博的超时时间
    public static int MARK_STATUS_TIMEOUT = 100;

    // 取置顶评论的超时时间
    public static int MARK_COMMENT_TIMEOUT = 100;

    // 取广告微博的超时时间
    public static int ADVERTISE_STATUS_TIMEOUT = 50;

    // 取热门微博的超时时间
    public static int HOT_TOP_TIMEOUT = 40;

    // 设置是否处于SHARE_RUN状态:对于该种状态，不保存content、featrue，
    // 不更新除content、vector之外的所有cache
    public static boolean SHARDING_SHARE_RUN = false;

    // from wuming1's apiNotice
    public static final String UNREAD_COUNT_ATCMT = "atcmt";
    public static final String UNREAD_COUNT_STATUS = "status";
    public static final String UNREAD_COUNT_FOLLOWER = "follower";
    public static final String UNREAD_COUNT_CMT = "cmt";
    public static final String UNREAD_COUNT_DM = "dm";
    public static final String UNREAD_COUNT_MENTION_STATUS = "mention_status";
    public static final String UNREAD_COUNT_MENTION_CMT = "mention_cmt";
    public static final String UNREAD_COUNT_COMMON_CMT = "close_friends_common_cmt";
    public static final String UNREAD_COUNT_CLOSE_ATTITUDE = "close_friends_attitude";
    public static final String UNREAD_COUNT_ATTITUDE = "attitude";
    public static final String UNREAD_COUNT_COMMENT_ATTITUDE = "comment_attitude";
    public static final String UNREAD_COUNT_OBJECT_ATTITUDE = "object_attitude";

    // 所有人的微博@提醒数
    public static final String UNREAD_COUNT_ALL_MENTION_STATUS = "all_mention_status";
    // 我关注人的微博@提醒数
    public static final String UNREAD_COUNT_ATTENTION_MENTION_STATUS = "attention_mention_status";
    // 所有人的评论@提醒数
    public static final String UNREAD_COUNT_ALL_MENTION_CMT = "all_mention_cmt";
    // 我关注人的评论@提醒数
    public static final String UNREAD_COUNT_ATTENTION_MENTION_CMT = "attention_mention_cmt";

    // 所有人的评论提醒数
    public static final String UNREAD_COUNT_ALL_CMT = "all_cmt";
    // 我关注人的评论提醒数
    public static final String UNREAD_COUNT_ATTENTION_CMT = "attention_cmt";
    // 所有人的粉丝提醒数
    public static final String UNREAD_COUNT_ALL_FOLLOWER = "all_follower";
    // 我关注人的粉丝提醒数
    public static final String UNREAD_COUNT_ATTENTION_FOLLOWER = "attention_follower";

    public static final String UNREAD_COUNT_PAGE_FOLLOWER = "page_follower";

    /**
     * this three are author filter
     */
    public static final int ALL = 0;
    public static final int FRIENDS = 1;
    public static final int STRANGER = 2;
    public static final int VERIFY = 3;
    public static final int CLOSEFRIENDS = 4;
    public static final int BOTHFRIENDS = 5;

    /**
     * feed type，原来在web中的Util中
     */
    public static final int TIMELINE_FEED = 0; // 时间线feed
    public static final int INTELLIGENT_FEED = 1; // 智能feed

    public static final int ATT_FEED = 2; // 亲密度排序
    public static final int MIX_FEED = 3; // 混排feed

    // 扩展二级索引类型
    public static final int EXT_TYPE_CLOSEFRIENDS = 1;

    // 用户设置的屏蔽类型
    public static final short FEEDFILTER_TYPE_STATUS = 0;
    public static final short FEEDFILTER_TYPE_APP = 1;
    public static final int FEEDFILTER_APPS_LIMITED = 100;
    public static final int FEEDFILTER_STATUSES_LIMITED = 500;

    // 消息类型定义
    public static final String MSG_TYPE_FEEDFILTER_ADD = "30"; // 添加屏蔽
    public static final String MSG_TYPE_FEEDFILTER_DELETE = "31"; // 解除屏蔽
    public static final String MSG_TYPE_FEEDFILTER_DELETEALL = "32";// 解除所有屏蔽

    public static final int STATUS_DELETE_BY_SELF = 0; // 微博的更新--用户自己
    public static final int STATUS_DELETE_BY_MANAGEMENT = 1;// 微博的更新--管理态
    public static final int STATUS_DELETE_BY_DEFAULT = STATUS_DELETE_BY_SELF;
    public static final int STATUS_DELETE_NO_CHANGE = -1; // 状态没有发生变更

    public static final int INBOX_VECTOR_LIMITED = 2000; // inbox vector上限个数

    public static final int CLOSEFRIENDS_TIMELINE_ALL = 0; // 密友可见微博+密友动态
    public static final int CLOSEFRIENDS_TIMELINE_STATUS = 1; // 密友可见微博
    public static final int CLOSEFRIENDS_TIMELINE_ACTIVITY = 2; // 密友动态

    // 赞/表态相关常量定义开始
    // 表态消息类型定义
    public static final String MSG_TYPE_ATTITUDE_ADD = "40";
    public static final String MSG_TYPE_ATTITUDE_DEL = "41";
    public static final String MSG_TYPE_ATTITUDE_LIKE = "42";
    public static final String MSG_TYPE_ATTITUDE_UNLIKE = "43";
    public static final String MSG_TYPE_LIKE_RECOMMEND_DATA_CLEAR = "44";

    // like db timeout
    public static long TIMEOUT_LIKE_DB_BATCH = 500;
    // 批量微博赞
    public static long TIMEOUT_STATUSES_ATTITUDE_BATCH = 500;
    // 单条微博赞
    public static long TIMEOUT_STATUSES_ATTITUDE = 100;

    // 对象喜欢列表长度限制
    public static int OBJECT_LIKE_LIST_VECTOR_SIZE = 200;
    // 赞/表态相关常量定义结束

    // 垃圾箱消息类型定义
    public static final String MSG_TYPE_TRASH_COMMENT_ADD = "73";
    public static final String MSG_TYPE_TRASH_COMMENT_DEL = "74";

    public static final String MSG_TYPE_CONTROL_EXPOSURE_ADD = "81";
    public static final String MSG_TYPE_CONTROL_EXPOSURE_DEL = "82";

    public static final String MSG_TYPE_CONTROL_EXT_META_UPDATE = "83";
    public static final String MSG_TYPE_CONTROL_EXT_META_DEL = "84";

    public static final String MSG_TYPE_CONTROL_EXT_VECTOR_UPDATE = "85";
    public static final String MSG_TYPE_CONTROL_EXT_VECTOR_DEL = "86";

    public static final int MAX_LIKE_VECTOR_SIZE = 2000;

    public static final int MAX_VSD_RECENT_DAYS = 3;
    public static final String USD_TIMELINE_SWITCHER = "feature.feed.usd.timeline";
    public static final String USD_UPDATE_SWITCHER = "feature.feed.usd.update";

    /**
     * 可见性微博，密友微博list_id
     */
    public static final int VISIBLE_CF_LIST_ID = 2;
    /**
     * 可见性微博， 双向关注微博list_id
     */
    public static final int VISIBLE_BI_LIST_ID = 3;

    /**
     * 是否有权限访问完整的user信息。<br>
     * AUTHORIZED：有权限获得完整信息；SEMI_AUTHORIZED：可以获得处理后的完整信息；NOT_AUTHORIZED：只能获得非完整信息
     */
    public static final int AUTHORIZED = 1;
    public static final int SEMI_AUTHORIZED = 2;
    public static final int NOT_AUTHORIZED = 3;


    public static final int STATUS_POST_SOURCE_NORMAL = 1;// 微博普通应用来源source的类型
    public static final int STATUS_CUSTOM_SOURCE = 2;// 微博自定义来源source的类型

    public static final int NORMAL_ATTITUDE_TYPE = 0;
    public static final int COMMON_ATTITUDE_TYPE = 1;
    public static final int COMMENT_ATTITUDE_TYPE = 2;
    public static final int OBJECT_ATTITUDE_TYPE = 3;


    public static enum PromotionType {
        TEXT_FOR_ACTIVE_USER(2, "未读微博", "", "unread status"), TEXT_FOR_INACTIVE_USER(2, "热门微博", "",
                "hot status"), TEXT_FOR_PROMO_TYPE1_6HOURS(1, "最近", "", "6 hours unread statuses"), TEXT_FOR_PROMO_TYPE1_24HOURS(1,
                        "24小时未读", "", "24 hours unread statuses");


        private PromotionType(int type, String zhCN, String zhHK, String enUS) {
            this.type = type;
            this.zhCN = zhCN;
            this.zhHK = zhHK;
            this.enUS = enUS;
        }

        private int type;
        private String zhCN;
        private String zhHK;
        private String enUS;

        public int getType() {
            return this.type;
        }

        public String getText(String lang) {
            if ("en_US".equals(lang)) {
                return this.enUS;
            } else if ("zh_HK".equals(lang)) {
                return this.zhHK;
            } else {
                return this.zhCN;
            }
        }
    }

    /**
     * 微博说明字样
     */
    public static final String WEIBO_SUFFIX = "-Weibo";

    public static enum PageType {
        ALL, PAGE_GROUP, PAGE_WITHOUT_GROUP
    }

    /**
     * 用户信用积分映射值存在以下关系 数据库中的值 展示的值 -1，0 80 -2 0 为便于阅读，特此封装该变量
     */
    public static final int USER_CREDIT_AS_ZERO = -2;

    // 客户端来源显示可点击 设置到customSource中的json key
    public static final String SOURCE_ALLOW_CLICK = "sourceAllowClick";
    // 1时表示来源可点击
    public static final int SOURCE_ALLOW_CLICK_Y = 1;
    // 0时表示为不可点
    public static final int SOURCE_ALLOW_CLICK_N = 0;
    // 客户端来源显示可点击功能 在蒋超他们白名单服务存放的key。
    public static final String ALLOW_CLICK_SOURCE = "allow_click_source";
}
