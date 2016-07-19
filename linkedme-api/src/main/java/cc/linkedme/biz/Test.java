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
        String b = "LinkedME,Demo";
        String c = "";
        String d = "Share";
        String e = "Live";
        String f =
                "{\"$og_title\":\"应用方\",\"$canonical_identifier\":\"\",\"$canonical_url\":\"\",\"$keywords\":[],\"$og_description\":\"\",\"$content_type\":\"\",\"$exp_date\":\"0\",\"$metadata\":{},\"$control\":{\"View\":\"https:\\/\\/www.linkedme.cc\\/iosdemo\\/apps.jsp?linkedme_key=linkedme_test_7e289a2484f4368dbafbd1e5c7d06903\",\"LinkedME\":\"Demo\"},\"source\":\"Android\"}";
        Joiner joiner = Joiner.on("&").skipNulls();
        System.out.println(joiner.join(a, b, c, d, e, f));
        System.out.println(MD5Utils.md5(joiner.join(a, b, c, d, e, f)));

    }
}
