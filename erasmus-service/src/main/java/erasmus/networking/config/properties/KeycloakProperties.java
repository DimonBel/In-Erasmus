package erasmus.networking.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
  private String clientId;
  private String clientSecret;
  private String authorizationGrantType;
  private String tokenUri;
}
