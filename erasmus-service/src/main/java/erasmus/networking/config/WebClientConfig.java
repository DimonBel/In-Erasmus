package erasmus.networking.config;

import erasmus.networking.config.properties.EventClientProperties;
import erasmus.networking.config.properties.KeycloakProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient keycloakWebClient(WebClient.Builder webClientBuilder, KeycloakProperties keycloakProperties) {
        return webClientBuilder
                .baseUrl(keycloakProperties.getTokenUri())
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    @Bean
    public WebClient eventServiceWebClient(WebClient.Builder webClientBuilder, EventClientProperties eventClientProperties) {
        return webClientBuilder
                .baseUrl(eventClientProperties.getUrl())
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}: {}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response Status Code: {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }
}
