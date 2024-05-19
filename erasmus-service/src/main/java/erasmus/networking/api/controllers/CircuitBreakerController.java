package erasmus.networking.api.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@CrossOrigin(origins = "*")
public class CircuitBreakerController {

  private final WebClient webClient;

  public CircuitBreakerController(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://api-gateway:8080").build();
  }

  @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
  @Retry(name = "backendA")
  @GetMapping("/circuitbreaker")
  public String circuitBreaker() {
    return webClient.get().uri("/api/event/dummy-url").retrieve().bodyToMono(String.class).block();
  }

  public String fallback(Throwable t) {
    return "Fallback Response due to error: " + t.getMessage();
  }
}
