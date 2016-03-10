package cc.linkedme.commons.uuid;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by LinkedME01 on 16/3/5.
 */
public class UuidCreatorTest {
    private static UuidCreator uuidCreator;
    static {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/commons-resource.xml"});
        ctx.start();
        System.out.println("context init sucess!");

        uuidCreator = (UuidCreator) ctx.getBean("uuidCreator");
    }

    @Test
    public void testNextId() {
        System.out.println(uuidCreator.nextId(0));
    }
}
