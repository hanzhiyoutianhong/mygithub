package cc.linkedme.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

/**
 * Created by qipo on 15/8/31.
 */
public class ShortUrlUtils {

    public static final char shortUrlCoder[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static int sixTwoValue(char c){
        for (int i = 0; i < shortUrlCoder.length; i++) {
            if( c == shortUrlCoder[i] ){
                return i;
            }
        }
        return -1;
    }

    public static String turnTenToSixTwo(long number){
        String result;

        StringBuilder tempStringBuilder = new StringBuilder(0);
        Stack<Character> stack = new Stack<Character>();
        Long rest = number;

        // if rest equal to zero, the result should be 0
        if (rest == 0) {
            result = "0";
            return result;
        }

        while (rest != 0) {
            stack.add(shortUrlCoder[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }

        while (!stack.isEmpty()) {
            tempStringBuilder.append(stack.pop());
        }

        result = tempStringBuilder.toString();

        return result;
    }

    public static long turnSixTwoToTen(String sixTwoStr){
        if (!StringUtils.isNotEmpty(sixTwoStr)) {
            sixTwoStr = "0";
        }
        long result = 0;
        long multiple = 1;
        Character c;

        for (int i = 0; i < sixTwoStr.length(); i++){
            c = sixTwoStr.charAt(sixTwoStr.length() - i - 1);
            result += sixTwoValue(c) * multiple;
            multiple = multiple * 62;
        }

        return result;
    }

    public static String linkIdentifier(String appKey, String type, String tags, String channel, String feature, String stage, String alias, String sdk, String data) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(appKey);
        buffer.append(type);
        buffer.append(tags);
        buffer.append(channel);
        buffer.append(feature);
        buffer.append(stage);
        buffer.append(alias);
        buffer.append(sdk);
        buffer.append(data);
        return MD5Utils.MD5ThirtyTwo(buffer.toString());
    }

    public static long linkAlgorithm(String md5){
        Long first = Long.valueOf(md5.substring(0, 8), 16);
        Long second = Long.valueOf(md5.substring(8, 16), 16);
        Long third = Long.valueOf(md5.substring(16, 24), 16);
        Long forth = Long.valueOf(md5.substring(24, 32), 16);
        Long result = first ^ second ^ third ^ forth;
        return result;
    }

    public static String identifyClickId(String appId, String type, String tags, String channel, String campaign, String feature, String stage, String alias, String data, String deepLinkPath) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(appId);
        buffer.append(type);
        buffer.append(tags);
        buffer.append(channel);
        buffer.append(campaign);
        buffer.append(feature);
        buffer.append(stage);
        buffer.append(alias);
        buffer.append(data);
        buffer.append(deepLinkPath);
        return MD5Utils.MD5ThirtyTwo(buffer.toString());

    }


}
