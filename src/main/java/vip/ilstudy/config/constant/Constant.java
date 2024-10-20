package vip.ilstudy.config.constant;

public class Constant {
    /**
     * 白名单
     */
    public static String[] WRITE_PATH = {"/login", "/register"};
    /**
     * 黑名单
     */
    public static String[] BLACK_PATH = {};
    /**
     * token 过期时间
     */
    public static Integer TOKEN_EXPIRE_TIME = 60 * 60 * 24;
    /**
     * 用户登录key
     */
    public static String LOGIN_TOKEN_KEY = "login_token_key";
    /**
     * 用户信息key
     */
    public static String LOGIN_USER_KEY = "login_user_key";
    /**
     * 用户请求token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * WebSocket request uri prefix
     */
    public static final String WEBSOCKET_REQUEST_PATH_PREFIX = "/websocket";
}
