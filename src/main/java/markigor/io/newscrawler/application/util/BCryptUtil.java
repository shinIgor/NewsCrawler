package markigor.io.newscrawler.application.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@UtilityClass
public class BCryptUtil {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encodePassword(String pw){
        String hashPassword = encoder.encode(pw);
        return hashPassword;
    }

    public Boolean isMatch(String hashPw,String pw){
        Boolean result = encoder.matches(hashPw, pw);
        return result;
    }

}
