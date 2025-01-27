package step.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import step.currencyconverter.domain.model.ConversionResult;
import step.currencyconverter.domain.model.data.CryptoCurrencyData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConversionService {

    private final FiatCurrencyRateService fiatService;
    private final CryptoCurrencyRateService cryptoService;

    @Autowired
    public ConversionService(FiatCurrencyRateService fiatService, CryptoCurrencyRateService cryptoService) {
        this.fiatService = fiatService;
        this.cryptoService = cryptoService;
    }

    public ConversionResult convert(String from, String to, double amount) {
        Map<String, Double> fiatToUsd = createFiatToUsdMap();
        Map<String, Double> cryptoToUsd = createCryptoToUsdMap();
        double amountInUsd = toUsd(from.toUpperCase(), amount, fiatToUsd, cryptoToUsd);
        double converted = fromUsd(to.toUpperCase(), amountInUsd, fiatToUsd, cryptoToUsd);
        return new ConversionResult(from, to, amount, converted);
    }

    private Map<String, Double> createFiatToUsdMap() {
        Map<String, Double> raw = fiatService.getFiatRates();
        Map<String, Double> result = new HashMap<>();
        for (Map.Entry<String, Double> e : raw.entrySet()) {
            result.put(e.getKey().toUpperCase(), 1.0 / e.getValue());
        }
        return result;
    }

    private Map<String, Double> createCryptoToUsdMap() {
        List<CryptoCurrencyData> allCrypto = cryptoService.getAllCryptoRates();
        return allCrypto.stream()
                .filter(c -> c.getQuotes() != null && c.getQuotes().get("USD") != null)
                .collect(Collectors.toMap(
                        c -> c.getSymbol().toUpperCase(),
                        c -> c.getQuotes().get("USD").getPrice(),
                        (existing, replacement) -> existing
                ));
    }

    private double toUsd(
            String currency,
            double amount,
            Map<String, Double> fiatToUsd,
            Map<String, Double> cryptoToUsd
    ) {
        if ("USD".equals(currency)) {
            return amount;
        }
        if (fiatToUsd.containsKey(currency)) {
            return amount * fiatToUsd.get(currency);
        }
        if (cryptoToUsd.containsKey(currency)) {
            return amount * cryptoToUsd.get(currency);
        }
        throw new IllegalArgumentException("Unknown currency: " + currency);
    }

    private double fromUsd(
            String currency,
            double usdAmount,
            Map<String, Double> fiatToUsd,
            Map<String, Double> cryptoToUsd
    ) {
        if ("USD".equals(currency)) {
            return usdAmount;
        }
        if (fiatToUsd.containsKey(currency)) {
            return usdAmount / fiatToUsd.get(currency);
        }
        if (cryptoToUsd.containsKey(currency)) {
            return usdAmount / cryptoToUsd.get(currency);
        }
        throw new IllegalArgumentException("Unknown currency: " + currency);
    }
}
