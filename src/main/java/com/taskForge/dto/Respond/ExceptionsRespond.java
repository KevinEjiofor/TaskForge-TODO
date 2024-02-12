package com.taskForge.dto.Respond;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ExceptionsRespond {
    String message;
    LocalDateTime timeDate;
    int  ErrorCode;


    public String getTimeDate(){
        if (timeDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'Time' hh:mm a");
            return timeDate.format(formatter);
        }
        return null;

    }
}
