package ch.bbcag.backend.ticketshop.security;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SecurityConstants {
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final long EXPIRATION_TIME = 864_000_000L; // 10 days
    public static final String DEFAULT_ROLE = "USER";
    public static final String[] API_DOCUMENTATION_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };
    private static final String SECRET = "Secret key to generate JWT's (min 256 bits)";
    private static final String ALGORITHM = "HmacSHA256";
    public static final SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), ALGORITHM);
}
