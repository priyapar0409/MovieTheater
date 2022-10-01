package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTests {

    @Test
    void isTotalFeeCorrectShow1() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                1,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0))
        );
        //totalFee should be calculated after considering the discounts.
        //This is a spcial movie would get 20% = 2.5
        //Being 1st show would get $3 discount
        //Biggest Discount = 3 so totalFee = (12.5-3)3 = 28.5
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 28.5);
        assertFalse(new Reservation(customer, showing, 3).totalFee() == 37.5);
    }

    @Test
    void isTotalFeeCorrectShow9Special() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                9,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0))
        );
        //totalFee should be calculated after considering the discounts.
        //This is a spcial movie would get 20% = 2.5
        //Biggest Discount = 2.5 so totalFee = (12.5-2.5)3 = 30
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 30);
        assertFalse(new Reservation(customer, showing, 3).totalFee() == 37.5);
    }


    @Test
    void isTotalFeeCorrectShowSpecialTime114() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                9,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0))
        );
        //totalFee should be calculated after considering the discounts.
        //This is a special movie would get 20% = 2.5
        //This is a 11-4 would get 25% = 3.125
        //Biggest Discount = 3.125 so totalFee = (12.5-3.125)3 = 28.125
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 28.125);
        assertFalse(new Reservation(customer, showing, 3).totalFee() == 37.5);
    }
    
}
