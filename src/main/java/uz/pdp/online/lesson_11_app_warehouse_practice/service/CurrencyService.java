package uz.pdp.online.lesson_11_app_warehouse_practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_11_app_warehouse_practice.entity.Currency;
import uz.pdp.online.lesson_11_app_warehouse_practice.payload.Result;
import uz.pdp.online.lesson_11_app_warehouse_practice.repository.CurrencyRepos;

import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepos currencyRepos;

    public Result addCurrency(Currency currency) {
        boolean existsByName = currencyRepos.existsByName(currency.getName());
        if (existsByName)
            return new Result("Ushbu valyuta MO da mavjud", false);
        currencyRepos.save(currency);
        return new Result("Ma'lumot qo'shildi", true);
    }

    public Page<Currency> getCurrenciesList(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Currency> currencyPage = currencyRepos.findAll(pageable);
        return currencyPage;
    }

    public Currency getCurrency(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepos.findById(id);
        if (!optionalCurrency.isPresent())
            return new Currency();
        return optionalCurrency.get();
    }

    public Result editCurrency(Integer id, Currency currency) {
        boolean exists = currencyRepos.existsByNameAndIdNot(currency.getName(), id);
        if (exists)
            return new Result("Bunday valyuta MO da mavjud",false);
        Optional<Currency> optionalCurrency = currencyRepos.findById(id);
        if (!optionalCurrency.isPresent())
            return new Result("Bunday valyuta topilmadi",false  );
        Currency editingCurrency = optionalCurrency.get();
        editingCurrency.setName(currency.getName());
        editingCurrency.setActive(currency.isActive());
        currencyRepos.save(editingCurrency);
        return new Result("Ma'lumot tahrirlandi",true);
    }

    public Result deleteCurrency(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepos.findById(id);
        if (!optionalCurrency.isPresent())
            return new Result("Ma'lumot topilmadi",false);
        currencyRepos.delete(optionalCurrency.get());
        return new Result("Ma'lumot o'chirild",true);
    }
}

