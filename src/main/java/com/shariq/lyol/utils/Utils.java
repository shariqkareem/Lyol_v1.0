package com.shariq.lyol.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.time.LocalTime;

public class Utils {
    public static LocalTime parseTimeFromCell(Cell cell){
        String timeString = new DataFormatter()
        .formatCellValue(cell)
        .replace("-PM", "")
        .replace("-AM", "");
        if(timeString.length()!=5)
            timeString = 0 + timeString;
        return LocalTime.parse(timeString);
    }
}
