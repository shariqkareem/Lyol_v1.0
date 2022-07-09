package com.shariq.lyol;

import com.shariq.lyol.service.ScheduleReaderService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SchedulerReaderTest {
    /*    --------- Test cases -------------
        1. Should save the day's schedule
        2. Should update the status of activities
        3. Should throw error when schedule file is of non-supported format
        4. Should throw error when schedule file does not have the correct column headers
        5. Should throw error when the values in the columns are wrong
     */
    @Autowired
    private ScheduleReaderService scheduleReaderService;
    @Value(value = "${scheduleFile.path}")
    private String filePath;

    @BeforeAll
    public static void setupTest(){

    }

    @Test
    public void shouldThrowErrorWhenScheduleDoesntExist(){
        Assumptions.assumeFalse(filePath.endsWith("schedule.csv"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> scheduleReaderService.readAndSaveSchedule());
    }

    @Test
    @Disabled
    public void shouldFailWhenColumnHeadersAreWrong(){
        Assertions.assertThrows(Exception.class, () -> scheduleReaderService.readAndSaveSchedule());
    }
}
