package uz.pdp.online.lesson_11_app_warehouse_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Currency;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.service.CurrencyService;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @PostMapping
    public Result addCurrency(@RequestBody Currency currency) {
        Result result = currencyService.addCurrency(currency);
        return result;
    }

    @GetMapping
    public Page<Currency> getCurrenciesList(@RequestParam int page) {
        Page<Currency> currenciesList = currencyService.getCurrenciesList(page);
        return currenciesList;
    }

    @GetMapping("/{id}")
    public Currency getCurrency(@PathVariable Integer id) {
        Currency currency = currencyService.getCurrency(id);
        return currency;
    }

    @PutMapping("/{id}")
    public Result editCurrency(@PathVariable Integer id, @RequestBody Currency currency) {
        Result result = currencyService.editCurrency(id, currency);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteCurrency(@PathVariable Integer id) {
        Result result = currencyService.deleteCurrency(id);
        return result;
    }
}
