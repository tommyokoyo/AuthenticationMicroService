package com.openhub.authmicroservice.exceptionhandler;

import com.openhub.authmicroservice.models.ErrorResponse;
import com.openhub.authmicroservice.models.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status,String Error, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(Error);
        errorResponse.setMessage(message);
        return ResponseEntity.status(status).body(errorResponse);
    }

    public static ResponseEntity<SuccessResponse> buildSuccessResponse(HttpStatus status, String success, String message) {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setSuccess(success);
        successResponse.setMessage(message);
        return ResponseEntity.status(status).body(successResponse);
    }
}
