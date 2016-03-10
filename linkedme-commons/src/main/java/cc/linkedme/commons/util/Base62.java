package cc.linkedme.commons.util;

public class Base62 {

    /**
     * code Table
     */
    private static final String ALPHABET = "ABCDEFGH_123456789IJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * encoding long num
     * @param num
     * @return
     */
    public static String encode(long num) {
        return encode(num, 62);
    }

    /**
     * decoding string
     * @param str
     * @return
     */
    public static long decode(String str) {
        return decode(str, 62);
    }
    /**
     * encoding long num base62
     * @param num
     * @param base
     * @return
     */
    private static String encode(long num, int base) {
        if (num < 1)
            throw new IllegalArgumentException("num must be greater than 0.");

        StringBuilder sb = new StringBuilder();
        for (; num > 0; num /= base) {
            sb.append(ALPHABET.charAt((int) (num % base)));
        }

        return sb.toString();
    }
    /**
     * decoding string base62
     * @param str
     * @param base
     * @return
     */
    private static long decode(String str, int base) {
        str = str.trim();
        if (str.length() < 1)
            throw new IllegalArgumentException("str must not be empty.");

        long result = 0;
        for (int i = 0; i < str.length(); i++) {
            result += ALPHABET.indexOf(str.charAt(i)) * Math.pow(base, i);
        }

        return result;

    }

    public static void main(String[] args) {
        long num = 1234567;
        System.out.println("Number: " + num);

        String encoding = null;
        long decoding;


        encoding = Base62.encode(num);
        System.out.println("Base62 Encoding: " + encoding);

        decoding = Base62.decode(encoding);
        System.out.println("Base62 Decoding: " + decoding);
    }

}