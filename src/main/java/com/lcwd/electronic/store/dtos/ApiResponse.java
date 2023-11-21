package com.lcwd.electronic.store.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

    private String message;
    private Boolean success;
}
