package markigor.io.newscrawler.application.util;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;

@UtilityClass
public class SHA256Util {

    public String encrypt(String s) {
        StringBuilder str = new StringBuilder();
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            mDigest.update(s.getBytes());

            byte[] msgStr = mDigest.digest();

            for (byte tmpStrByte : msgStr) {
                String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);

                str.append(tmpEncTxt);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return str.toString();
    }
}
