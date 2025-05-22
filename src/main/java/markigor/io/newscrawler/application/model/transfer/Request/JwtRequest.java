package markigor.io.newscrawler.application.model.transfer.Request;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtRequest {
      Long usn;
      Long userId;
      String name;
      Boolean isConnect;

    public JwtRequest(Claims claims) {
        this.usn = claims.get("usn", Long.class);
        this.userId = claims.get("userId", Long.class);
        this.name = claims.get("Name", String.class);
        this.isConnect = claims.get("isConnect", Boolean.class);
    }
}
