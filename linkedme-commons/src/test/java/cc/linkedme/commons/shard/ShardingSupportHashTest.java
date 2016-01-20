package cc.linkedme.commons.shard;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.linkedme.commons.redis.JedisPort;

@SuppressWarnings("unchecked")
public class ShardingSupportHashTest {
//    private static ShardingSupportHash<JedisPort> sh;
//    private static String key;
//    private static long id;

//    static {
//        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/redis.xml"});
////        ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/redis.xml");
//        ctx.start();
//        System.out.println("context init sucess!");
//        sh = (ShardingSupportHash<JedisPort>) ctx.getBean("mgetShardingSupport");
//        key = "abcd";
//        id = 12345L;
//    }

    ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/redis.xml");
    ShardingSupportHash<JedisPort> sh = (ShardingSupportHash<JedisPort>)app.getBean("mgetShardingSupport");

    public String key = "abcde";
    public String id = "12345L";

    @Test
    public void testGetClient() {
        JedisPort client1 = sh.getClient(key);
        JedisPort client2 = sh.getClient(id);

        client1.set(key, key + "value");
        client2.set(String.valueOf(id), id + "value");

        System.out.println(client1.get(key));
        System.out.println(client2.get(key));
    }

    @Test
    public void testGetDbTable() {
        DbTable table1 = sh.getDbTable(key);
        JedisPort client1 = sh.getClientByDb(table1.getDb());
        client1.set(key, key + "value");
        System.out.println(client1.get(key));

        DbTable table2 = sh.getDbTable(id);
        JedisPort client2 = sh.getClientByDb(table2.getDb());
        client2.set(String.valueOf(id), id + "value");
        System.out.println(client2.get(String.valueOf(id)));
    }

}
