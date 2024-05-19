package erasmus.networking.api.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import erasmus.networking.api.security.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import erasmus.networking.api.security.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

  private final JWTUtil jwtUtil;

  private final UserDetailsService userDetailsService;

  private final List<RequestMatcher> requestMatchers =
      Arrays.asList(
          new AntPathRequestMatcher("/auth/**"),
          new AntPathRequestMatcher("/h2-console/**"),
          new AntPathRequestMatcher("/swagger-ui/**"),
          new AntPathRequestMatcher("/v3/api-docs/**"),
          new AntPathRequestMatcher("/swagger-resources/**"),
          new AntPathRequestMatcher("/error"),
          new AntPathRequestMatcher("/actuator/**"));

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return requestMatchers.stream().anyMatch(matcher -> matcher.matches(request));
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var authHeader = request.getHeader("Authorization");

//    if (authHeader == null
//        || (authHeader.contains("Bearer") && authHeader.length() < 7)
//        || authHeader.isBlank()
//        || !authHeader.contains("Bearer")) {
//      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//      log.error("Invalid JWT Token in Authorization header! It is blank.");
//      return;
//    }
//
//    var jwt = authHeader.substring(7);
//
//    try {
//      var username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
//
//      var userDetails = userDetailsService.loadUserByUsername(username);
//
//      var authToken =
//          new UsernamePasswordAuthenticationToken(
//              userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//
//      if (SecurityContextHolder.getContext().getAuthentication() == null) {
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//      }
//    } catch (JWTVerificationException ex) {
//      log.error("Invalid JWT Token! Your token has not passed validation.", ex);
//      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//      return;
//    }

    filterChain.doFilter(request, response);
  }
}
