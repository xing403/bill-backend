package vip.ilstudy.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vip.ilstudy.config.Constant;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.utils.JwtTokenUtils;
import vip.ilstudy.utils.UUIDUtils;

import java.util.*;

@Component
public class TokenService {
    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    public String generateToken(LoginUserEntity loginUser) {
        String userKey = UUIDUtils.getUUID();
        loginUser.setToken(userKey);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.LOGIN_USER_KEY, userKey);

        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return userKey Map
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getTokenKey(String uuid) {
        return Constant.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 校验 token
     *
     * @param request
     */
    public void verifyToken(HttpServletRequest request) throws Exception {
        try {
            String token = JwtTokenUtils.getToken(request);
            if (StringUtils.hasText(token))
                parseToken(token);
        } catch (ExpiredJwtException e) {
            throw new Exception("Token已过期");
        } catch (UnsupportedJwtException e) {
            throw new Exception("不支持的 token 格式");
        } catch (MalformedJwtException e) {
            throw new Exception("错误的token 格式");
        } catch (SignatureException e) {
            throw new Exception("签名失败");
        } catch (IllegalArgumentException e) {
            throw new Exception("非法参数异常");
        }
    }

}
