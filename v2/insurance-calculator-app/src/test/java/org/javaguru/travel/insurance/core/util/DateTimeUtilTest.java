package org.javaguru.travel.insurance.core.util;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeUtilTest {

    private final DateTimeUtil dateTimeUtil = new DateTimeUtil();

    @Test
    void getDaysBetweenTestValid() {
        LocalDate dateFrom = LocalDate.of(2020, 1, 1);
        LocalDate dateTo = LocalDate.of(2020, 1, 3);

        assertEquals(2, dateTimeUtil.getDaysBetween(dateFrom, dateTo));
    }

    @Test
    void getDaysBetweenTestInvalidNegative() {
        LocalDate dateFrom = LocalDate.of(2020, 1, 3);
        LocalDate dateTo = LocalDate.of(2020, 1, 1);

        assertEquals(-2, dateTimeUtil.getDaysBetween(dateFrom, dateTo));
    }

    @Test
    void getDaysBetweenTestInvalidZero() {
        LocalDate dateFrom = LocalDate.of(2020, 1, 1);
        LocalDate dateTo = LocalDate.of(2020, 1, 1);

        assertEquals(0, dateTimeUtil.getDaysBetween(dateFrom, dateTo));
    }
}