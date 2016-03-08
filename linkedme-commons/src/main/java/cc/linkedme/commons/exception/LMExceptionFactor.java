package cc.linkedme.commons.exception;

import java.io.Serializable;

/**
 * Created by qipo on 15/9/29.
 */
public class LMExceptionFactor implements Serializable {

    private static final long serialVersionUID = 425760202285010132L;

    private int errorCode;
    private String errorMessageEn;
    private String errorMessageCn;


    public LMExceptionFactor(int errorCode, String errorMessageEn, String errorMessageCn) {
        this.errorCode = errorCode;
        this.errorMessageEn = errorMessageEn;
        this.errorMessageCn = errorMessageCn;
    }

    /**
     * 系统错误
     */

    public static final LMExceptionFactor LM_SYS_ERROR = new LMExceptionFactor(20000, "system error", "系统错误");

    /**
     * 缺少参数
     */

    public static final LMExceptionFactor LM_SHORT_PARAMETER = new LMExceptionFactor(20001, "short parameter", "缺少参数");

    /**
     * 非法的参数值
     */
    public static final LMExceptionFactor LM_ILLEGAL_PARAMETER_VALUE = new LMExceptionFactor(20002, "illegal parameter value", "非法的参数值");

    /**
     * 数据库操作失败
     */
    public static final LMExceptionFactor LM_FAILURE_DB_OP = new LMExceptionFactor(20003, "failure on database operation", "数据库操作失败");

    /**
     * 协议不支持
     */
    public static final LMExceptionFactor LM_UNSUPPORTED_PROTOCOL = new LMExceptionFactor(20004, "unsupported protocol!", "协议不支持");

    /**
     * 请求过于频繁
     */
    public static final LMExceptionFactor LM_FREQUENCY_REQUEST = new LMExceptionFactor(20005, "frequency request", "请求过于频繁");

    /**
     * 请求过期
     */
    public static final LMExceptionFactor LM_EXPIRED_REQUEST = new LMExceptionFactor(20006, "request is expired", "过期的请求");

    /**
     * 无短连接
     */
    public static final LMExceptionFactor LM_NOT_SHORT_URL = new LMExceptionFactor(20007, "don't have the short url", "无短连接");

    /**
     * ip限制
     */
    public static final LMExceptionFactor LM_IP_LIMIT = new LMExceptionFactor(20008, "IP limit!", "IP限制，不能请求该资源!");

    /**
     * 任务过多，系统繁忙
     */
    public static final LMExceptionFactor LM_SYSTEM_BUSY = new LMExceptionFactor(20009, "too many pending tasks, system is busy!", "任务过多，系统繁忙!");

    /**
     * 非法请求
     */
    public static final LMExceptionFactor LM_ILLEGAL_REQUEST = new LMExceptionFactor(20010, "Illegal Request!", "非法请求!");

    /**
     * 接口不存在
     */
    public static final LMExceptionFactor LM_API_NOT_EXIST = new LMExceptionFactor(20011, "Api not found!", "接口不存在!");

    /**
     * 接口认证失败
     */
    public static final LMExceptionFactor LM_AUTH_FAILED = new LMExceptionFactor(20012, "Auth failed!", "接口认证失败!");

    /** Http 方法错误 */
    public static final LMExceptionFactor LM_METHOD_ERROR =
            new LMExceptionFactor(20013, "HTTP METHOD is not suported for this request!", "请求的HTTP METHOD不支持!");

    /** Http 方法错误 */
    public static final LMExceptionFactor LM_UUID_ERROR =
            new LMExceptionFactor(20014, "Creat uuid failed!", "发号失败!");

    /**
     * get and set
     */

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessageEn() {
        return errorMessageEn;
    }

    public void setErrorMessageEn(String errorMessageEn) {
        this.errorMessageEn = errorMessageEn;
    }

    public String getErrorMessageCn() {
        return errorMessageCn;
    }

    public void setErrorMessageCn(String errorMessageCn) {
        this.errorMessageCn = errorMessageCn;
    }
}
