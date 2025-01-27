package step.currencyconverter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient coinPaprikaWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.coinpaprika.com/v1")
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(configurer ->
                                        configurer.defaultCodecs()
                                                .maxInMemorySize(10 * 1024 * 1024)
                                )
                                .build()
                )
                .build();
    }
}
