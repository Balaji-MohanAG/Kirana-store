package com.jarapplication.kiranastore.exception;

import com.jarapplication.kiranastore.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionController {

    /**
     * userName doesn't exist Exception
     * @param e
     * @return
     */
    @ExceptionHandler(value = UserNameExistsException.class)
    public Object UserNameExistsException(UserNameExistsException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setStatus("error");
        apiResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * Illegal Argument Exception
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Object IllegalArgumentException(IllegalArgumentException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setStatus("error");
        apiResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @ExceptionHandler(value = RateLimitExceededException.class)
    public Object RateLimitExceededException(RateLimitExceededException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setStatus("error");
        apiResponse.setErrorMessage(e.getMessage());
        apiResponse.setStatus("429");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Object handleSpringRequestBodyException(HttpMessageNotReadableException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setStatus("10002");
        apiResponse.setErrorMessage("Invalid Request Body");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Object handleSpringRequestParamException(MissingServletRequestParameterException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setStatus("10002");
        apiResponse.setErrorMessage("Invalid Request Parameter");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @ExceptionHandler(value = Exception.class)
    public Object handleException(Exception e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setStatus("error");
        apiResponse.setErrorMessage("Something went wrong. Please try again later!");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
