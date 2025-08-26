package org.javaguru.travel.insurance.core.util;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class DateTimeUtil {

    public long getDaysBetween(LocalDate dateFrom, LocalDate dateTo) {
        return ChronoUnit.DAYS.between(dateFrom, dateTo);
    }

    public LocalDate getCurrentDate() {
        ZoneId moscowZone = ZoneId.of("Europe/Moscow");
        return LocalDate.now(moscowZone);
    }
}
