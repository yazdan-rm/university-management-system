package ir.ums.utils;

import com.github.mfathi91.time.PersianDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class DateConverterUtil {

    public static String dateToShamsiWithSeparator(LocalDate gregorianDate, String separator) {
        PersianDate persianDate = PersianDate.fromGregorian(gregorianDate);
        return String.format("%04d" + separator + "%02d" + separator + "%02d",
                persianDate.getYear(), persianDate.getMonthValue(), persianDate.getDayOfMonth());
    }

    public static String dateToShamsi(LocalDate gregorianDate) {
        PersianDate persianDate = PersianDate.fromGregorian(gregorianDate);
        return String.format("%04d/%02d/%02d",
                persianDate.getYear(), persianDate.getMonthValue(), persianDate.getDayOfMonth());
    }

    public static List<String> localDateTimeToShamsi(LocalDateTime gregorianDateTime) {
        LocalDate localDate = gregorianDateTime.toLocalDate();
        LocalTime localTime = gregorianDateTime.toLocalTime();
        PersianDate persianDate = PersianDate.fromGregorian(localDate);

        String formattedDate = String.format("%04d/%02d/%02d",
                persianDate.getYear(), persianDate.getMonthValue(), persianDate.getDayOfMonth());
        String formattedTime = String.format("%02d:%02d", localTime.getHour(), localTime.getMinute());

        return List.of(formattedDate, formattedTime);
    }
}
