package com.shariq.lyol.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.shariq.lyol.enums.ActivityStatus;
import com.shariq.lyol.models.Activity;
import com.shariq.lyol.models.Reason;
import com.shariq.lyol.models.Schedule;
import com.shariq.lyol.repositories.ScheduleRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@Service
@ConditionalOnProperty(
        value="scheduleFile.type",
        havingValue = "csv",
        matchIfMissing = true)
public class ScheduleReaderServiceCsv implements ScheduleReaderService{
    private final ScheduleRepo scheduleRepo;
    @Value(value = "${scheduleFile.path}")
    private String filePath;

    @Autowired
    public ScheduleReaderServiceCsv(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    public void readAndSaveSchedule() throws Exception {
        Schedule schedule = scheduleRepo.findByDate(LocalDate.now()).orElse(new Schedule());
        File scheduleFile = new File(filePath);
        if (!scheduleFile.exists())
            throw new IllegalArgumentException("Schedule file does not exist at location " + filePath);
        CSVParser parser = new CSVParserBuilder().withSeparator(',')
                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH).withIgnoreLeadingWhiteSpace(true).withEscapeChar('\0')
                .withIgnoreQuotations(false).withQuoteChar('"').withStrictQuotes(false).build();
        try (BufferedReader br = new BufferedReader(new FileReader(scheduleFile));
             CSVReader csvReader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {
            String[] row = csvReader.readNext();
            if(!validateHeaders(row))
                throw new InputMismatchException("Column headers are wrong");
            while ((row = csvReader.readNext())!=null) {
                if(row.length==1 && row[0]==null)
                    break;
                String activityId = schedule.getDate().toString() + " " + row[0].trim();
                boolean isNewActivity = false;
                Activity activity = schedule.getActivities().stream()
                        .filter(activity1 -> activity1.getId().equals(activityId)).findFirst()
                        .orElse(Activity.builder().id(activityId).build());
                if(activity.getActivity() == null)
                    isNewActivity = true;
                activity.setActivity(row[1]);
                activity.setLifeSection(row[2]);
                activity.setStartTime(LocalTime.parse(row[3].trim()));
                activity.setEndTime(LocalTime.parse(row[4].trim()));
                activity.setImportant(Boolean.parseBoolean(row[5].trim()));
                activity.setActivityStatus(ActivityStatus.getKey(row[6].trim()));
                activity.setReasonsForNotCompleting(readReasons(row[7]));
                if(isNewActivity)
                    schedule.getActivities().add(activity);
            }
            scheduleRepo.save(schedule);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error when reading schedule file. Refer stacktrace for details");
        }
    }

    private boolean validateHeaders(String[] row) {
        return row.length == 8 &&
                (row[0].trim().equals("Id")
                && row[1].trim().equals("Activity")
                && row[2].trim().equals("Life section")
                && row[3].trim().equals("Start time")
                && row[4].trim().equals("End time")
                && row[5].trim().equals("isImportant")
                && row[6].trim().equals("Activity status")
                && row[7].trim().equals("Reasons for not completing")
        );
    }

    private List<Reason> readReasons(String stringCellValue) {
        if(StringUtils.isBlank(stringCellValue))
            return new ArrayList<>();
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
