package ir.ums.dao.course;

import ir.ums.utils.DateConverterUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class CourseDaoImplTest {

    @Test
    void getCurrentSemester() {
        String currentShamsiDate = DateConverterUtil.dateToShamsi(LocalDate.now());
        int currentYear = Integer.parseInt(currentShamsiDate.split("/")[0]) - 1000;
        int currentMonth = Integer.parseInt(currentShamsiDate.split("/")[1]);
        if ((currentMonth >= 7 && currentMonth < 11) || (currentMonth >= 1 && currentMonth <= 3))
            System.out.println(currentYear + "1");
        else if (currentMonth == 11 || currentMonth == 12)
            System.out.println(currentYear + "2");
        else if (currentMonth >= 4 && currentMonth < 7)
            System.out.println(currentYear + "3");
    }
}
