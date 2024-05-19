package erasmus.networking.config.properties;

import jakarta.validation.constraints.NotNull;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTConfigProperties {

  @NotNull private RSAPublicKey publicKey;

  @NotNull private RSAPrivateKey privateKey;

  private String audience;

  private String issuer;

  private int expirationTimeAccessTokenMn;

  private int expirationTimeRefreshTokenMn;
}
