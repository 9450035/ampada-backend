package com.ampadabackend.tms.controller.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BodyException {

    private final String message;

    private final Integer statusCode;

    private final String title;

}
