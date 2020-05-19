package cloud.liyan.springbootapi;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author liyan
 * @description
 * @date 2020-05-19 17:46
 */
public class TokenUtil {
    private static final String TOKEN_PREFIX = "Bearer";

    private static final String TOKEN_SECRET = "KLJDKFLOIHAIOFHLAKJKASJFLJKALJF675HFu6LKHFvk6";
    private static int EXPIRATION_TIME = 500;

    public static String getToken(Map<String, Object> claims) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, EXPIRATION_TIME);
        Date date = calendar.getTime();
        return Jwts.builder().setClaims(claims).setExpiration(date).signWith(SignatureAlgorithm.ES256, TOKEN_SECRET).compact();
    }

    public Map<String, Object> validateTokenAndGetClaims(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
    }
}
