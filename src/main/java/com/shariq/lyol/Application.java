package com.shariq.lyol;

import com.shariq.lyol.service.DayAnalysisService;
import com.shariq.lyol.service.ScheduleReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
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
        int option = sc.nextInt();
        switch(option){
            case 1 :
            case 2 : {
                System.out.println("Please provide path of schedule file ");
                String filePath = sc.next();
                scheduleReaderService.readAndSaveSchedule(filePath);
                break;
            }
            case 3 : {
                System.out.println("Day's score : " + dayAnalysisService.calculateDaysScore());
            }
        }

    }
}
