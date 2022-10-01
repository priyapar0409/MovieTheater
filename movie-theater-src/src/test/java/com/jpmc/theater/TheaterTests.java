package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TheaterTests {

    @Test
    void totalFeeForCustomerShow1() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 1, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        //since The special show 20% is not valid as this is not a special show
        //the reservation for 1: 2022-10-01T09:00 Turning Red (1 hour 25 minutes) $11.0 would get $3 off due it being 1st show
        //11-3 = 8*4 = 32
        assertEquals(reservation.totalFee(), 32);
        assertNotEquals(reservation.totalFee(), 44);
    }

	@Test
    void totalFeeForCustomerShow2() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 2, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        //the resrvation for 2: 2022-10-01T11:00 Spider-Man: No Way Home (1 hour 30 minutes) $12.5 would get 25% off due it being 11-4 slot
        //12.5-25% = 9.375*4 = 37.5
        assertEquals(reservation.totalFee(), 37.5);
        assertNotEquals(reservation.totalFee(), 50);
    }


	@Test
    void totalFeeForCustomerShow3() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 3, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        //the resrvation for 3: 2022-10-01T12:50 The Batman (1 hour 35 minutes) $9.0 would get 25% off due it being 11-4 slot as the highest discount.
        //the 20% special would not apply
        //9-25% = 6.75*4 = 27
        assertEquals(reservation.totalFee(), 27);
        assertNotEquals(reservation.totalFee(), 36);
    }

	@Test
    void totalFeeForCustomerShow4() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 4, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        //the resrvation for 4: 2022-10-01T14:30 Turning Red (1 hour 25 minutes) $11.0 would get 25% off due it being 11-4 slot
        //11-25% = 8.25*4 = 33
        assertEquals(reservation.totalFee(), 33);
        assertNotEquals(reservation.totalFee(), 44);
    }


	@Test
    void totalFeeForCustomerShow5() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 5, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        //the resrvation for 5: 2022-10-01T16:10 Spider-Man: No Way Home (1 hour 30 minutes) $12.5 would get 20% off due it not being 11-4 slot
        //12.5-20% = 10*4 = 40
        assertEquals(reservation.totalFee(), 40);
        assertNotEquals(reservation.totalFee(), 50);
    }


	
	@Test
    void totalFeeForCustomerShow6() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 6, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        //the resrvation for 6: 2022-10-01T17:50 The Batman (1 hour 35 minutes) $9.0 would not get any discount as this is not a special show, nor 1st or 2nd sequence, nor between 11 to 4.
        //Only exception this would get a dollar discount would be on 7th of the month.
        //9-0 = 9*4 = 36 
        assertEquals(reservation.totalFee(), 36);
    }

	@Test
    void totalFeeForCustomerShow7() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 7, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        //the resrvation for 7: 2022-10-01T19:30 Turning Red (1 hour 25 minutes) $11.0 would not get any discount as this is not a special show, nor 1st or 2nd sequence, nor between 11 to 4.
        //Only exception this would get a dollar discount would be on 7th of the month.
        //11-0 = 11*4 = 44
        assertEquals(reservation.totalFee(), 44);
    }
	
	@Test
    void totalFeeForCustomerShow8() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 8, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        //the resrvation for 8: 2022-10-01T21:10 Spider-Man: No Way Home (1 hour 30 minutes) $12.5 $12.5 would get 20% off due it being a special show
        //12.5-20% = 10*4 = 40
        assertEquals(reservation.totalFee(), 40);
        assertNotEquals(reservation.totalFee(), 50);
    }

	
	
	@Test
    void totalFeeForCustomerShow9() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 9, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        //the resrvation for 9: 2022-10-01T23:00 The Batman (1 hour 35 minutes) $9.0 would not get any discount as this is not a special show, nor 1st or 2nd sequence, nor between 11 to 4.
        //9-0 = 9*4 = 36
        //Only exception this would get a dollar discount would be on 7th of the month.
        assertEquals(reservation.totalFee(), 36);
    }

		
	//Following cases will only pass on the 7th f the month if same theatre schedule is maintained.

	
	/*
	 * @Test void totalFeeForCustomerShow6OnDay7() { Theater theater = new
	 * Theater(LocalDateProvider.singleton()); Customer john = new
	 * Customer("John Doe", "id-12345"); Reservation reservation =
	 * theater.reserve(john, 6, 4); // System.out.println("You have to pay " +
	 * reservation.getTotalFee()); //the resrvation for 6: 2022-10-01T17:50 The
	 * Batman (1 hour 35 minutes) $9.0 would not get any discount as this is not a
	 * special show, nor 1st or 2nd sequence, nor between 11 to 4. //Only exception
	 * this would get a dollar discount would be on 7th of the month. //9-0 = 9*4 =
	 * 36 assertEquals(reservation.totalFee(), 36); }
	 */

	
	

	/*
	 * @Test void totalFeeForCustomerShow7OnDay7() { Theater theater = new
	 * Theater(LocalDateProvider.singleton()); Customer john = new
	 * Customer("John Doe", "id-12345"); Reservation reservation =
	 * theater.reserve(john, 7, 4); // System.out.println("You have to pay " +
	 * reservation.getTotalFee()); //the resrvation for 7: 2022-10-01T19:30 Turning
	 * Red (1 hour 25 minutes) $11.0 would not get any discount as this is not a
	 * special show, nor 1st or 2nd sequence, nor between 11 to 4. //11-1 = 10*4 =
	 * 40 assertEquals(reservation.totalFee(), 40);
	 * assertNotEquals(reservation.totalFee(), 44); }
	 */
	 

	
	/*
	 * @Test void totalFeeForCustomerShow9OnDay7() { Theater theater = new
	 * Theater(LocalDateProvider.singleton()); Customer john = new
	 * Customer("John Doe", "id-12345"); Reservation reservation =
	 * theater.reserve(john, 9, 4); // System.out.println("You have to pay " +
	 * reservation.getTotalFee()); //the resrvation for 9: 2022-10-01T23:00 The
	 * Batman (1 hour 35 minutes) $9.0 would not get any discount as this is not a
	 * special show, nor 1st or 2nd sequence, nor between 11 to 4. //9-1 = 8*4 = 32
	 * //Only exception this would get a dollar discount would be on 7th of the
	 * month. assertEquals(reservation.totalFee(), 32);
	 * assertNotEquals(reservation.totalFee(), 36); }
	 */


    //trying to reserve for an invalid show:
    @Test
    void reserveInvalidShow() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 10, 4);
        assertNotNull(reservation);
        
    }




    @Test
    void printMovieSchedule() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }
}
