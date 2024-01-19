package com.taskForge.dto.Respond;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRespond<T> {
    private T data;

}
