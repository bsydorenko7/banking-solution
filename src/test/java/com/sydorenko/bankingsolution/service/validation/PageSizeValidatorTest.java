package com.sydorenko.bankingsolution.service.validation;

import com.sydorenko.bankingsolution.exception.BadRequestException;
import com.sydorenko.bankingsolution.service.validation.PageSizeValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PageSizeValidatorTest {

    @Test
    void validatePageSize_shouldNotThrowException_whenSizeIsLessThanOrEqualToMaxSize() {
        assertDoesNotThrow(() -> PageSizeValidator.validatePageSize(1000));
    }

    @Test
    void validatePageSize_shouldThrowBadRequestException_whenSizeIsGreaterThanMaxSize() {
        assertThrows(BadRequestException.class, () -> PageSizeValidator.validatePageSize(3000));
    }
}
