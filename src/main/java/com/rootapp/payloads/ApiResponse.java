package com.rootapp.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse {

    private String message;
    private boolean success;

}
