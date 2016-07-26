package cc.linkedme.biz;

import cc.linkedme.commons.util.MD5Utils;
import com.google.common.base.Joiner;

/**
 * Created by wrshine on 16/1/11.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("hello linkedme!");
        String a = "linkedme_test_7e289a2484f4368dbafbd1e5c7d06903";
        String tags = "";
        String channel = "";
        String feature = "";
        String stage = "";
        String params =
                "{\"$control\":{\"control\":\"LinkedME\",\"View\":\"https://www.linkedme.cc/iosdemo/intro.jsp\"},\"$og_title\":\"DetailViewController\"}";
        Joiner joiner = Joiner.on("&").skipNulls();
        System.out.println(joiner.join(a, tags, channel, feature, stage, params));
        System.out.println(MD5Utils.md5(joiner.join(a, tags, channel, feature, stage, params)));

    }
}
