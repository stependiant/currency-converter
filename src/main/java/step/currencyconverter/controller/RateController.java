package step.currencyconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import step.currencyconverter.domain.model.data.CryptoCurrencyData;
import step.currencyconverter.service.CryptoCurrencyRateService;
import step.currencyconverter.service.FiatCurrencyRateService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rates")
public class RateController {

    private final FiatCurrencyRateService fiatService;
    private final CryptoCurrencyRateService cryptoService;

    @Autowired
    public RateController(FiatCurrencyRateService fiatService, CryptoCurrencyRateService cryptoService) {
        this.fiatService = fiatService;
        this.cryptoService = cryptoService;
    }

    @GetMapping("/fiat")
    public Map<String, Double> getFiatRates() {
        return fiatService.getFiatRates();
    }

    @GetMapping("/crypto")
    public List<CryptoCurrencyData> getAllCryptoRates() {
        return cryptoService.getAllCryptoRates();
    }

    @GetMapping("/crypto/{id}")
    public CryptoCurrencyData getCryptoCurrencyById(@PathVariable String id) {
        return cryptoService.getCryptoCurrencyDataById(id);
    }
}
