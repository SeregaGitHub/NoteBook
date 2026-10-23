package ru.kraser.two_token_security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kraser.two_token_security.model.ApiResponse;

import java.time.LocalDateTime;

import static ru.kraser.two_token_security.util.Utilities.*;

@RestController
@Slf4j
@RequestMapping(path = BASE_URL)
public class Controller {

    @GetMapping(path = ADMIN_URL)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAdmin() {
        LocalDateTime now = LocalDateTime.now();

        log.info("Getting response from ADMIN_URL...");

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Response from Admin url")
                .status(200)
                .httpStatus(HttpStatus.OK)
                .timestamp(now)
                .build();

        log.info("Response from ADMIN_URL received successfully.");

        return apiResponse;
    }

    @GetMapping(path = TECHNICIAN_URL)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getTechnician() {
        LocalDateTime now = LocalDateTime.now();

        log.info("Getting response from TECHNICIAN_URL...");

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Response from Technician url")
                .status(200)
                .httpStatus(HttpStatus.OK)
                .timestamp(now)
                .build();

        log.info("Response from TECHNICIAN_URL received successfully.");

        return apiResponse;
    }

    @GetMapping(path = EMPLOYEE_URL)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getEmployee() {
        LocalDateTime now = LocalDateTime.now();

        log.info("Getting response from EMPLOYEE_URL...");

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Response from Employee url")
                .status(200)
                .httpStatus(HttpStatus.OK)
                .timestamp(now)
                .build();

        log.info("Response from EMPLOYEE_URL received successfully.");

        return apiResponse;
    }

    @GetMapping(path = DEFAULT_URL)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDefault() {
        LocalDateTime now = LocalDateTime.now();

        log.info("Getting response from DEFAULT_URL...");

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Response from Default url")
                .status(200)
                .httpStatus(HttpStatus.OK)
                .timestamp(now)
                .build();

        log.info("Response from DEFAULT_URL received successfully.");

        return apiResponse;
    }

    @GetMapping(path = OPEN_URL)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getOpen() {
        LocalDateTime now = LocalDateTime.now();

        log.info("Getting response from OPEN_URL...");

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Response from Open url")
                .status(200)
                .httpStatus(HttpStatus.OK)
                .timestamp(now)
                .build();

        log.info("Response from OPEN_URL received successfully.");

        return apiResponse;
    }
}

