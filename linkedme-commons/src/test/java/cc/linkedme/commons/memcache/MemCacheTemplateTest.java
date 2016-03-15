package cc.linkedme.commons.memcache;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by LinkedME01 on 16/3/13.
 */
public class MemCacheTemplateTest {
    private static MemCacheTemplate memCacheTemplate;
    static {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/mc.xml"});
        ctx.start();
        System.out.println("context init sucess!");

        memCacheTemplate = (MemCacheTemplate) ctx.getBean("testMemCache");
    }

    @Test
    public void testSet() {

        memCacheTemplate.set("adfa", "afaadfvalue");
    }

    @Test
    public void testGet() {
        System.out.println(memCacheTemplate.get("adfa"));
    }
}
