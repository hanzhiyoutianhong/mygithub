package cc.linkedme.commons.utils;

import java.util.Random;

/**
 * Created by qipo on 15/8/30.
 */

public class RandomUtils {

    public static String getCharAndNumr(int length) {
        String val = "";

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // output char or number

            if ("char".equalsIgnoreCase(charOrNum)) {
                //int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // lower case or upper case
                int choice = 65;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }


}
