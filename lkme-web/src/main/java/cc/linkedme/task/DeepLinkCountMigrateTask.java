package cc.linkedme.task;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.service.DeeplinkCountMigrateService;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by LinkedME01 on 16/6/23.
 */
public class DeepLinkCountMigrateTask implements Runnable {

    @Resource
    DeeplinkCountMigrateService deeplinkCountMigrateService;

    @Override
    public void run() {
        ApiLogger.info("migrate task start...");

        //call service
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        deeplinkCountMigrateService.migrateRedisCountDataToMysql(df.format(date));

    }
}
