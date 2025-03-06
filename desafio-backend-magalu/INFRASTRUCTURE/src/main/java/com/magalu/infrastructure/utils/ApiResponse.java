package com.magalu.infrastructure.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.magalu.domain.validation.Error;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Estrutura genérica para respostas da API")
public class ApiResponse<T> {
    //todo traduzir para inglês

    @Schema(description = "Informational message about the request status")
    @JsonProperty private String message;

    @JsonProperty private boolean isSuccess;

    @Schema(description = "Data returned in the response")
    @JsonProperty private T data;

    @Schema(description = "List of errors, if any")
    @JsonProperty private List<Error> errors;

    @Schema(description = "HTTP status of the request")
    @JsonProperty private int statusCode;

    @Schema(description = "Response timestamp")
    @JsonProperty private long timestamp;

    @Schema(description = "Endpoint route called")
    @JsonProperty private String path;


    protected ApiResponse(
            final boolean isSuccess,
            final String message,
            final T data,
            final List<Error> erros,
            final int statusCode,
            final long timestamp,
            final String path) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
        this.errors = erros;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.path = path;
    }

    public static <T> ApiResponse<T> createSuccess(
            final String message,
            final T data,
            final List<Error> errors,
            final int statusCode,
            final String path) {

        return new ApiResponse<T>(
                true,
                message,
                data,
                errors,
                statusCode,
                System.currentTimeMillis(),
                path
        );
    }

    public static <T> ApiResponse<T> createFailed(
            final String message,
            final T data,
            final List<Error> errors,
            final int statusCode,
            final String path) {

        return new ApiResponse<T>(
                false,
                message,
                data,
                errors,
                statusCode,
                System.currentTimeMillis(),
                path
        );
    }

    public static <T> ApiResponse<T> createFailed(
            final String message,
            final List<Error> errors,
            final int statusCode,
            final String path) {

        return new ApiResponse<T>(
                false,
                message,
                null,
                errors,
                statusCode,
                System.currentTimeMillis(),
                path
        );
    }

}
