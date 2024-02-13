package com.shariq.lyol;

import com.shariq.lyol.service.DayAnalysisService;
import com.shariq.lyol.service.ScheduleReaderService;
import com.shariq.lyol.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    private final DayAnalysisService dayAnalysisService;
    private final ScheduleService scheduleService;
    private final ScheduleReaderService scheduleReaderService;
    @Value(value = "${isTestRun}")
    private boolean isTestRun;

    @Autowired
    public Application(DayAnalysisService dayAnalysisService, ScheduleService scheduleService, ScheduleReaderService scheduleReaderService) {
        this.dayAnalysisService = dayAnalysisService;
        this.scheduleService = scheduleService;
        this.scheduleReaderService = scheduleReaderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("************* Live your life *************");
        System.out.println();
        System.out.println();

        System.out.println("Select an option ");
        System.out.println("1. Provide day's schedule");
        System.out.println("2. Update the progress");
        System.out.println("3. Check day's score");
        System.out.println("4. Show options");
        System.out.println("5. Exit");
        System.out.println("6. Clear day's schedule");

        Scanner sc= new Scanner(System.in);
        while(!isTestRun) {
            switch (sc.nextInt()) {
                case 1:
                case 2: {
                    scheduleReaderService.readAndSaveSchedule();
                    log.info("-------- Schedule activities updated -------");
                    break;
                }
                case 3: {
                    dayAnalysisService.calculateDaysScore();
                    break;
                }
                case 4:
                    System.out.println("1. Provide day's schedule");
                    System.out.println("2. Update the progress");
                    System.out.println("3. Check day's score");
                    System.out.println("4. Show options");
                    System.out.println("5. Exit");
                    System.out.println("6. Clear day's schedule");
                    break;
                case 5:
                    log.info("-------- Bye Bye -------");
                    return;
                case 6:
                    log.info("Clearing schedule");
                    scheduleService.clearSchedule();
                    log.info("Cleared schedule");
                    break;
                default:
                    log.error("!!! Invalid option. Select one of the following !!!");
                    System.out.println("1. Provide day's schedule");
                    System.out.println("2. Update the progress");
                    System.out.println("3. Check day's score");
                    System.out.println("4. Show options");
                    System.out.println("5. Exit");
            }
        }
    }
}
