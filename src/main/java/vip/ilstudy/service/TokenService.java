package vip.ilstudy.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.utils.UUIDUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenService {
    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    public String generateToken(LoginUserEntity loginUser) {
        String token = UUIDUtils.getUUID();
        loginUser.setToken(token);

        Map<String, Object> claims = new HashMap<>();
        claims.put("login_user_token", token);

        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return token;
    }

    /**
     * 根据 token 获登录用户参数
     *
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

    }
}
