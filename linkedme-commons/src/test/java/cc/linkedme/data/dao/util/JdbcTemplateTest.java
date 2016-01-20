package cc.linkedme.data.dao.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcTemplateTest {
    private static JdbcTemplate jt;

    static {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/mysql.xml"});
        ctx.start();
        System.out.println("context init sucess!");
        jt = (JdbcTemplate) ctx.getBean("comment_jdbctemplate_0");
    }

    @Test
    public void testQuery() {
        //jt.query(psc, rse);
    }

    @Test
    public void testExecute() {
        //jt.execute(sql, action, isWrite);
    }

}
