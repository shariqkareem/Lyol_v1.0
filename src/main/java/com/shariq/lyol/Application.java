package com.shariq.lyol;

import com.shariq.lyol.service.DayAnalysisService;
import com.shariq.lyol.service.ScheduleReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    private final ScheduleReaderService scheduleReaderService;
    private final DayAnalysisService dayAnalysisService;

    @Autowired
    public Application(ScheduleReaderService scheduleReaderService, DayAnalysisService dayAnalysisService) {
        this.scheduleReaderService = scheduleReaderService;
        this.dayAnalysisService = dayAnalysisService;
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
        System.out.println("4. Add Life sections");
        System.out.println("5. Add Blockers");

        Scanner sc= new Scanner(System.in);
        while(true) {
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
                case 7:
                    log.info("-------- Bye Bye -------");
                    return;
                default:
                    log.error("!!! Invalid option. Select one of the following !!!");
                    System.out.println("1. Provide day's schedule");
                    System.out.println("2. Update the progress");
                    System.out.println("3. Check day's score");
                    System.out.println("4. Add Life sections");
                    System.out.println("5. Add Blockers");
                    System.out.println("6. Show options");
                    System.out.println("7. Exit");
            }
        }
    }
}
