package cc.linkedme.commons.util;

import java.util.Calendar;
import java.util.Date;

public class UuidHelper {

    public static final long TIME_BIT = Long.MAX_VALUE << UuidConst.IDC_SEQ_BIT_LENGTH;
    public static final long IDC_ID_BIT = 15L << UuidConst.SEQ_BIT_LENGTH;
    public static final long BIZ_FLAG_BIT = 3 << UuidConst.SPEC_SEQ_HA_BIT_LENGTH;

    public static final long TIME1_BIT = Long.MAX_VALUE << 34;// 30个1+34个0
    public static final long TIME2_BIT = 1073741823L << 4; // 30个11+4个0
    public static final long FLAG_BIT = 15L; //4个1

    public static final long MIN_VALID_ID = 3000000000000000L;
    public static final long MAX_VALID_ID = 4500000000000000L;

    public static boolean isUuidAfterUpdate(long id) {
        return UuidHelper.isValidId(id) && id > 3342818919841793L; // 微博id升级后的一个id, 2011-08-05 00:00:00  
    }

    public static boolean isCommentMidAfterUpdate(long id) {
        return UuidHelper.isValidId(id) && id > 3557855061995026L; //评论mid改造后的一个id，2013-3-20 09:16:50
    }

    /**
     * is valid id
     *
     * @param id
     * @return
     */
    public static boolean isValidId(long id) {
        return (id > MIN_VALID_ID) && (id < MAX_VALID_ID);
    }

    /**
     * get unix time from id (Accurate to seconds)
     *
     * @param id
     * @return
     */
    public static long getTimeFromId(long id) {
        return getTimeNumberFromId(id) + UuidConst.ID_OFFSET;
    }

    /**
     * get time number from id
     *
     * @param id
     * @return
     */
    public static long getTimeNumberFromId(long id) {
        return id >> UuidConst.IDC_SEQ_BIT_LENGTH;
    }

    /**
     * get idc from id
     *
     * @param id
     * @return
     */
    public static long getIdcIdFromId(long id) {
        return (id & IDC_ID_BIT) >> UuidConst.SEQ_BIT_LENGTH;
    }

    /**
     * get seq from id
     *
     * @param id
     * @return
     */
    public static long getSeqFromId(long id) {
        if (isSpecUuid(id)) {
            return (id >> 1) % UuidConst.SPEC_SEQ_LIMIT;
        } else {
            return id % UuidConst.SEQ_LIMIT;
        }
    }

    /**
     * get unix time from id, start from 2014-01-01 00:00:00
     * <pre>UNIT:seconds</pre>
     *
     * @param id
     * @return
     */
    public static long getUnixTimeFromId(long id) {
        return (id + UuidConst.ID_OFFSET_2014) * 1000;
    }

    /**
     * get 30 bit timestamp from id，start from 2014-01-01 00:00:00
     * <pre>UNIT:seconds</pre>
     *
     * @param id
     * @return
     */
    public static long getTime30FromId(long id) {
        long actualId = id / 1000 - UuidConst.ID_OFFSET_2014;
        return actualId > 0 ? actualId : 0;
    }

    /**
     * get biz flag for spec uuid
     *
     * @param id
     * @return
     */
    public static long getBizFlag(long id) {
        if (isSpecUuid(id)) {
            return (id & BIZ_FLAG_BIT) >> UuidConst.SPEC_SEQ_HA_BIT_LENGTH;
        }
        return -1;
    }

    public static boolean isSpecIdc(long idcFlag) {
        for (long specIdc : UuidConst.SPEC_IDC_FLAGS) {
            if (specIdc == idcFlag) {
                return true;
            }
        }
        return false;
    }

    /**
     * get date time from id
     *
     * @param id
     * @return
     */
    public static Date getDateFromId(long id) {
        return new Date(getTimeFromId(id) * 1000);
    }

    /**
     * check if the uuid is spec uuid
     *
     * @param id
     * @return
     */
    private static boolean isSpecUuid(long id) {
        long idcId = getIdcIdFromId(id);
        return isSpecIdc(idcId);
    }

    /**
     * get timestamp from id with second precision.
     *
     * @param id
     * @return
     */
    public static long getTimeStampFromId(long id) {
        return (getTimeNumberFromId(id) + UuidConst.ID_OFFSET) * 1000;
    }

    public static void main(String[] args) {
        long id = 3000000000000000L;
//    	long id = 3379782484330149l;
//    	long id = 3363475030378149l; //10.1 3363475030378149
//    	long id = 3374709054211749l; //11.1 3374709054211749
//    	long id = 3100365840449541l;
//    	System.out.println(getTimeFromId(id) * 1000);
//    	System.out.println(new Date(UuidHelper.getTimeFromId(id) * 1000));
//    	SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
//		System.out.println(format.format(UuidHelper.getTimeFromId(id) * 1000));

//    	long id = getIdByDate(new Date());
        System.out.println(id);
        System.out.println(getDateFromId(id));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}
