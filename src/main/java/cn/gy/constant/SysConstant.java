package cn.gy.constant;

/**
 * @author kuyun
 * @date 2018年7月1日
 * @desc
 */
public class SysConstant {

    public static final Integer RESPONSE_CODE_SUCCESS = 800;

    public static final String RESPONSE_MSG_SUCCESS = "success";

    public static final Integer RESPONSE_CODE_FAILED = 900;

    public static final String RESPONSE_MSG_FAILED = "操作失败";

    public static final Integer RESPONSE_CODE_INVALID_PARAMETER = 810;

    public static final String RESPONSE_MSG_INVALID_PARAMETER = "参数为空或者参数不合法";


    public static final String X_AXIS = "xAxis";

    public static final Byte YES = 1;
    public static final Byte NO = 0;

    public static final byte SUCCESS = 0;
    public static final byte FAIL = 1;
    public static final byte LOG_DOING = 2;
    public static final byte LOG_QUEUED = 3;
    public static final byte LOG_TIMEOUT = 4;


    public static final Byte BACKUP_MANUAL = 1;
    public static final Byte BACKUP_PERIODICALLY = 0;

    public final static int INTERVAL_STAT_DAY = 7;
    public static final int MANUAL_DELAY = 5;

    public static final String TENANT_INST_LIST = "TENANT_INST_LIST";


    public static final Byte READY = 0;
    public static final Byte DOING = 1;
    public static final Byte DONE = 2;
    public static final Byte QUEUED = 3;

    public static final Byte STATE_OK = 0;
    public static final Byte STATE_UNKNOWN = 1;
    public static final Byte STATE_WARNING = 2;
    public static final Byte STATE_CRITICAL = 3;

    public static final String STRING_DELIM = ",";

    public static final int RETRY_LIMIT = 3;

    /*一个月的毫秒数,默认token失效时间*/
    public static final Long ONEMONTH_MILLS = 2592000000L;

    /* access_token 根据tokenId记录tokenSecret和apikey的redis前缀 */
    public static final String REDIS_TOKEN_ID = "access_token_id:";

    /* access_token 根据apikey记录tokenId的redis前缀 */
    public static final String REDIS_TOKEN_APIKEY = "access_apikey:";

    /* redis  根据ak查找sk前缀 */
    public static final String REDIS_APIKEY = "aksk_apikey:";

    /* redis  根据role_id查找三级菜单前缀 */
    public static final String REDIS_ROLE_MENU = "menu_role_id:";

    /* redis  根据apikey查找审核标准*/
    public static final String REDIS_AUDIT_APIKEY = "audit_apikey:";

    /* 获取access_token分布式锁的key */
    public static final String REDIS_TOKEN_LOCK = "access_token_lock:";

    public static final String SESSION_USER_CUSTOMER_ID = "__USER_CUSTOMER_ID";
    public static final String SESSION_USER_CUSTOMER_NAME = "__USER_CUSTOMER_NAME";
    public static final String SESSION_USER_ACCOUNT_ID = "__USER_ACCOUNT_ID";
    public static final String SESSION_USER_API_KEY = "__USER_API_KEY";
    public static final String SESSION_USER_ACCOUNT = "__USER_ACCOUNT";
    public static final String SESSION_USER_RESET = "__IF_RESET_PASSWD";

    public static final String SESSION_LOGIN_VO = "__LOGIN_VO";

    public static final String SYSTEM_NAME = "系统";
    public static final int INITIAL_MAP_CAPACITY = 1<<4;
    public static final String SUCCESS_MSG = "SUCCESS";
    public static final String COMMA = ",";
    public static final String SINGLE_QUOTES = "'";
    public static final String CHANNEL_REGEX = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
}


