package cc.linkedme.data.dao.util;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.strategy.TableContainer;

public class JdbcTemplateTest {
    private static TableContainer tableContainer;
    private static JdbcTemplate jt;

    static {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/mysql.xml"});
        ctx.start();
        System.out.println("context init sucess!");

        tableContainer = (TableContainer) ctx.getBean("tableContainer");
    }

    @Test
    public void testQuery() {
        TableChannel channel = tableContainer.getTableChannel("test", "GET_USER", 12345L, 12345L);
        String sql = channel.getSql();
        Dog dog = (Dog) channel.getJdbcTemplate().query(sql, new String[]{"mike"}, new ResultSetExtractor() {
            public Dog extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Dog dog = new Dog();
                    dog.setAge(rs.getInt("age"));
                    dog.setName(rs.getString("name"));
                    return dog;
                }
                return null;
            }
        });
        System.out.println(dog.toString());
    }

    @Test
    public void testUpdate() {
        //jt.execute(sql, action, isWrite);
        int result = 0;
        TableChannel channel = tableContainer.getTableChannel("test", "ADD_USER", 12345L, 12345L);
        String sql = channel.getSql();

        try {
            result = channel.getJdbcTemplate().update(sql, new Object[]{"Andy", 20});
        } catch (DataAccessException e) {
            throw e;
        }
        System.out.println("update row count:" + result);
    }

    class Dog {
        private String name;
        private int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String toString() {
            return this.age + " " + this.name;
        }
    }

}
