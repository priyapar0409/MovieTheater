package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
	

    @Test
    void specialMovieWith20PercentDiscount() {
    	//Ticket Price = $10
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),10, 1);
        
        //20% discount for the special movie -- since this test can run between 11 and 4, hardcoding the time so we can explicitly test for Special Show
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)));
        assertEquals(8, spiderMan.calculateTicketPrice(showing));

        System.out.println(Duration.ofMinutes(90));
    }
    
    @Test
    void Day7DollarDiscount() {
    	//Ticket Price = $10
        //Avoiding Special movie discounts by seeting the special to anything but 1
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),10, 2);
        
	//Any movies showing on 7th, you'll get 1$ discount - setting the date to Oct 7th, avoiding 11am to 4pm by explicitly setting to 9 am
        Showing showing1 = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.parse("2022-10-07"), LocalTime.of(9, 0)));
        assertEquals(9, spiderMan.calculateTicketPrice(showing1));
        
        System.out.println(Duration.ofMinutes(90));
    }

    @Test
    void Showing1Dollar3Discount() {
    	//Ticket Price = $10
        //Avoiding Special movie discounts by seeting the special to anything but 1
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),10, 2);
        
        //$3 discount for the movie showing 1st of the day , avoiding 11am to 4pm by explicitly setting to 9 am
        Showing showing2 = new Showing(spiderMan, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)));
        assertEquals(7, spiderMan.calculateTicketPrice(showing2));

        System.out.println(Duration.ofMinutes(90));
    }

    @Test
    void Showing2Dollar2Discount() {
    	//Ticket Price = $10
        //Avoiding Special movie discounts by seeting the special to anything but 1
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),10, 2);
        
        //$2 discount for the movie showing 2nd of the day, avoiding 11am to 4pm by explixitly setting to 9 am
        Showing showing3 = new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0)));
        assertEquals(8, spiderMan.calculateTicketPrice(showing3));

        System.out.println(Duration.ofMinutes(90));
    }

    @Test
    void Time25PercentDiscount() {
    	//Ticket Price = $10
        //Avoiding Special movie discounts by seeting the special to anything but 1
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),10, 2);
        
        //Any movies showing starting between 11AM ~ 4pm, you'll get 25% discount
        Showing showing4 = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0)));
        assertEquals(7.5, spiderMan.calculateTicketPrice(showing4));

        System.out.println(Duration.ofMinutes(90));
    }

}
