package step.currencyconverter.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import step.currencyconverter.domain.model.data.CryptoCurrencyData;

import java.util.Arrays;
import java.util.List;

@Service
public class CryptoCurrencyRateService {

    private static final String COINPAPRIKA_BASE_URL = "https://api.coinpaprika.com/v1";

    private final WebClient webClient;

    public CryptoCurrencyRateService(@Qualifier("coinPaprikaWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<CryptoCurrencyData> getAllCryptoRates() {
        CryptoCurrencyData[] responseArray = webClient
                .get()
                .uri("/tickers")
                .retrieve()
                .bodyToMono(CryptoCurrencyData[].class)
                .block();

        if (responseArray != null) {
            return Arrays.asList(responseArray);
        }
        return List.of();
    }

    public CryptoCurrencyData getCryptoCurrencyDataById(String id) {
        return webClient
                .get()
                .uri("/tickers/{id}", id)
                .retrieve()
                .bodyToMono(CryptoCurrencyData.class)
                .block();
    }
}
