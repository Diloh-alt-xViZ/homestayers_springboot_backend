package com.developer.homestayersbackend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateConverter {

    public static LocalDateTime convertSqlDateToLocalDateTime(java.sql.Date sqlDate) {
        LocalDate localDate = sqlDate.toLocalDate();
        return localDate.atStartOfDay();
    }
}
