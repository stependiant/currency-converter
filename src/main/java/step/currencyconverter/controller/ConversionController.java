package step.currencyconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import step.currencyconverter.domain.model.ConversionResult;
import step.currencyconverter.service.ConversionService;

@RestController
@RequestMapping("/api")
public class ConversionController {

    private final ConversionService conversionService;

    @Autowired
    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/convert")
    public ConversionResult convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount
    ) {
        return conversionService.convert(from, to, amount);
    }
}
