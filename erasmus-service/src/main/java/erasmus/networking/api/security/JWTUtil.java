package erasmus.networking.api.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import erasmus.networking.api.responses.jwt.JWT;
import erasmus.networking.config.properties.JWTConfigProperties;
import erasmus.networking.domain.model.entity.Authority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JWTUtil {

  private final JwtEncoder encoder;

  private final JwtDecoder jwtDecoder;

  private final JWTConfigProperties jwtConfigProperties;

  public JWT generateJWT(String email, Set<Authority> authorities) {

    var expirationTime = Instant.now().plusSeconds(jwtConfigProperties.getExpirationTimeAccessTokenMn() * 60L);
    var claimsSet =
        JwtClaimsSet.builder()
            .issuer(jwtConfigProperties.getIssuer())
            .issuedAt(Instant.now())
            .expiresAt(expirationTime)
            .subject(email)
            .claim("authorities", populateAuthorities(authorities))
            .build();

    JWT jwt = new JWT();
    jwt.setExpiryDuration(expirationTime);
    jwt.setUsername(email);
    jwt.setRoles(populateAuthorities(authorities));
    jwt.setToken(encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue());

    return jwt;
  }

  public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
    Jwt decodedJwt = jwtDecoder.decode(token);
    return decodedJwt.getSubject();
  }

  private String populateAuthorities(Collection<? extends Authority> collection) {
    Set<String> authoritiesSet = new HashSet<>();
    collection.forEach(authority -> authoritiesSet.add(authority.getName()));
    return String.join(",", authoritiesSet);
  }
}
