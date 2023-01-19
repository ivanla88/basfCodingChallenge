package com.basf.codingtest.chemicals.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class DateUtilsTest {

    @Test
    public void givenADateAndAFormat_thenReturnDateInParamFormat() throws ParseException {

        LocalDate input = LocalDate.of(2021, 11, 29);
        String sDate = "20211129";
        LocalDate result = DateUtils.parseStringDate(sDate, "yyyyMMdd");
        assertTrue(input.equals(result));
    }

}