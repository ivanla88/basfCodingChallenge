package com.basf.codingtest.chemicals.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="ApiResponseError", description="Information about any exception raised")
public class ApiResponseError {

    @Schema(description="Date and time of the exception")
    private LocalDateTime timestamp;

    @Schema(description="Error message of the exception", example = "Error parsing file", type = "String")
    private String message;
}
