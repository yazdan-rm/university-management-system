package ir.ums.service.sms;

import ir.ums.utils.DateConverterUtil;
import ir.ums.utils.SMSTemplateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@SpringBootTest
class SMSImplTest {

    @Autowired
    ISMS smsService;

    @Test
    void sendSMSToUser() {
        var listOfDateAndTime = DateConverterUtil.localDateTimeToShamsi(LocalDateTime.now());
        String messageText = SMSTemplateUtil.generateMessage("sms-login", Map.of("dateTime", listOfDateAndTime.getFirst() + " " + listOfDateAndTime.get(1)));
        var response = smsService.sendSMSToUser(messageText, "09033300462", "123");
        System.out.println(response);
    }

    @Test
    void testConverterToShamsi() {
        LocalDateTime today = LocalDateTime.now();
        System.out.println(DateConverterUtil.dateToShamsiWithSeparator(today.toLocalDate(), "⭐"));
        System.out.println(DateConverterUtil.dateToShamsi(LocalDate.now()));
        System.out.println(DateConverterUtil.localDateTimeToShamsi(today));
        var listOfDateAndTime = DateConverterUtil.localDateTimeToShamsi(today);
        System.out.println(listOfDateAndTime.getFirst() + " :تاریخ  " + listOfDateAndTime.get(1) + " :ساعت");
    }
}