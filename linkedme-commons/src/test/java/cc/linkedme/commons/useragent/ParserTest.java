package cc.linkedme.commons.useragent;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by LinkedME01 on 16/4/1.
 */
public class ParserTest {

    @Test
    public void testParse() throws Exception {

    }

    @Test
    public void testParseUserAgent() throws Exception {
//        String webUserAgent = "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36";
        String iosUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_2_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13D15 Safari/601.1";
//        String AndroidUserAgent = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36";
//        String ucUserAgent = "Mozilla/5.0 (Linux; Android 5.1.1; YQ607 Build/LMY47V) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.11 Mobile Safari/537.36";
//        String iosUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_2_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13D15 Safari/601.1";
        String AndroidUserAgent = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36";
        Parser parser = new Parser();
        Client client = parser.parse(AndroidUserAgent);
        //String userAgentFamily =  client.userAgent.family;
        //String userAgentMajor =  client.userAgent.major;
        String osFamily = client.os.family;
        String osMajor = client.os.major;

        System.out.println(client.device);
        System.out.println(client.device.family);

//        System.out.println(userAgentFamily);
//        System.out.println(userAgentMajor);
        System.out.println(osFamily);
        System.out.println(osMajor);
    }

    @Test
    public void testParseDevice() throws Exception {


    }

    @Test
    public void testParseOS() throws Exception {

    }
}