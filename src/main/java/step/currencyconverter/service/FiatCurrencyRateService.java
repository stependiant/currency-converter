package step.currencyconverter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import step.currencyconverter.domain.model.data.FiatCurrencyData;

import java.util.Map;

@Service
public class FiatCurrencyRateService {

    @Value("${freecurrencyapi.key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.freecurrencyapi.com/v1/latest";

    private final WebClient webClient;

    public FiatCurrencyRateService() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL).build();
    }

    public Map<String, Double> getFiatRates() {
        FiatCurrencyData response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(FiatCurrencyData.class)
                .block();
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return Map.of();
    }

}
