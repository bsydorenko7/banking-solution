package com.sydorenko.bankingsolution.service.validation;

import com.sydorenko.bankingsolution.mapper.exception.BadRequestException;

public class PageSizeValidator {

    private static final int MAX_SIZE = 2000;

    public static void validatePageSize(int size) {
        if (size > MAX_SIZE) {
            throw new BadRequestException("Parameter size has value: " + size +
                    " that greater than allowed: " + MAX_SIZE);
        }
    }
}
