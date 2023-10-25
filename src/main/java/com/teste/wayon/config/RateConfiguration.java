package com.teste.wayon.config;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RateConfiguration {

    private List<Rate> rates = new ArrayList<>();

    public RateConfiguration() {
        // Configura as taxas aqui com base nos dias de transferência
        rates.add(new Rate(0, 0,"2.5%"));
        rates.add(new Rate(1, 10, "0.0%"));
        rates.add(new Rate(11, 20, "8.2%"));
        rates.add(new Rate(21, 30, "6.9%"));
        rates.add(new Rate(31, 40, "4.7%"));
        rates.add(new Rate(41, 50, "1.7%"));
    }

    public Optional<Rate> getRateForDays(int days) {
        return rates.stream()
                .filter(rate -> rate.isInRange(days))
                .findFirst();
    }
}

