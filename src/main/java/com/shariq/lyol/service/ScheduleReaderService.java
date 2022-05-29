package com.shariq.lyol.service;

import com.shariq.lyol.enums.ActivityStatus;
import com.shariq.lyol.models.*;
import com.shariq.lyol.repositories.ScheduleRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ScheduleReaderService {
    private final ScheduleRepo scheduleRepo;

    @Autowired
    public ScheduleReaderService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public void readAndSaveSchedule(String filePath) throws Exception {
        Schedule schedule = Schedule.builder()
                .date(LocalDate.now())
                .activities(new ArrayList<>())
                .score(0)
                .build();
        try {
            File scheduleFile = new File(filePath);
            if (!scheduleFile.exists())
                throw new IllegalArgumentException("Schedule file does not exist at location " + filePath);

            XSSFWorkbook workbook = new XSSFWorkbook(scheduleFile);
            XSSFSheet scheduleSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = scheduleSheet.iterator();
            rowIterator.next(); // skipping header
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Activity activity = new Activity();
                activity.setActivity(cellIterator.next().getStringCellValue());
                activity.setLifeSection(cellIterator.next().getStringCellValue());
                activity.setActivityStatus(ActivityStatus.getKey(cellIterator.next().getStringCellValue()));
                //activity.setStartTime(LocalTime.parse(new DataFormatter().formatCellValue(cellIterator.next()).replace("-PM", "").replace("-AM", "")));
                activity.setStartTime(LocalTime.parse("05:45"));
                //activity.setEndTime(LocalTime.parse(new DataFormatter().formatCellValue(cellIterator.next())));
                activity.setEndTime(LocalTime.parse("12:45"));
                activity.setImportant(row.getCell(5).getBooleanCellValue());
                activity.setReasonsForNotCompleting((row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().equals("")) ? null : readReasons(row.getCell(6).getStringCellValue()));

                schedule.getActivities().add(activity);
                scheduleRepo.save(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error when reading schedule file. Refer stacktrace for details");
        }
    }

    private List<Reason> readReasons(String stringCellValue) {
        List<Reason> reasons = new ArrayList<>();
        String[] reasonsArray;
        if(stringCellValue.contains("&"))
            reasonsArray = stringCellValue.split("&");
        else
            reasonsArray = new String[]{stringCellValue};
        for (String s : reasonsArray) {
            String[] split = s.split("\\|");
            Reason reason = new Reason();
            reason.setReason(split[0]);
            reason.setBlocker(split[1]);
            reasons.add(reason);
        }
        return reasons;
    }
}
