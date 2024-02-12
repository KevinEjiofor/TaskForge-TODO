package com.taskForge.dto.Respond;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor

public class ApiRespond<T> {
    private T data;

}
