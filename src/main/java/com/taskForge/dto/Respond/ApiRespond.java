package com.taskForge.dto.Respond;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRespond<T> {
    private T data;
    private String error;
    public ApiRespond(T data){
        this.data = data;
    }

    public ApiRespond(String error) {
        this.error = error;
    }

}
