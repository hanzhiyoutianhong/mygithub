package cc.linkedme.mcq;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.mcq.reader.StartReadingAble;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class McqProcessorStarter implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private String[] startProcessorNames = ArrayUtils.EMPTY_STRING_ARRAY;

    public void setStartProcessorNames(String startProcessorNameStr) {
        if (StringUtils.isBlank(startProcessorNameStr)) {
            return;
        }
        this.startProcessorNames = startProcessorNameStr.split(",");
        for (int i = 0; i < startProcessorNames.length; i++) {
            startProcessorNames[i] = startProcessorNames[i].trim();
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        for (String name : this.startProcessorNames) {
            StartReadingAble processor = (StartReadingAble) this.applicationContext.getBean(name);
            ApiLogger.info("McqProcessor " + name + " start reading.");
            processor.startReading();
        }
    }
}
