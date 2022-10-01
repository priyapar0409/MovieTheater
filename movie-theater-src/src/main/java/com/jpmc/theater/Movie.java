package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing);
    }

    private double getDiscount(Showing showing) {
        
    	List<Double> discountList = new ArrayList<Double>();
        
    	//Converting all existing double discount variables to Double so they can be used along with Lists and comparators for easier comparision.
    	Double specialDiscount = Double.valueOf(0);
        if (MOVIE_CODE_SPECIAL == specialCode) {
            specialDiscount = ticketPrice * 0.2;  // 20% discount for special movie
            discountList.add(specialDiscount);
        }

        Double sequenceDiscount = Double.valueOf(0);
        if (showing.getSequenceOfTheDay() == 1) {
            sequenceDiscount  = Double.valueOf(3); // $3 discount for 1st show
            discountList.add(sequenceDiscount);
        } else if (showing.getSequenceOfTheDay() == 2) {

            sequenceDiscount  = Double.valueOf(2); // $2 discount for 2nd show
            discountList.add(sequenceDiscount);
        }
//        else {
//            throw new IllegalArgumentException("failed exception");
//        }

        Double timeDiscount  = Double.valueOf(0);
        //(int year, int month, int dayOfMonth, int hour, int minute) {
        if (showing.getStartTime().isAfter(LocalDateTime.of(showing.getStartTime().getYear(), showing.getStartTime().getMonth(), showing.getStartTime().getDayOfMonth(), 10, 59))
        		&& showing.getStartTime().isBefore(LocalDateTime.of(showing.getStartTime().getYear(), showing.getStartTime().getMonth(), showing.getStartTime().getDayOfMonth(), 16, 00))) {
        	timeDiscount = ticketPrice * 0.25;  //Any movies showing starting between 11AM ~ 4pm, you'll get 25% discount
        	discountList.add(timeDiscount);
        	}       
        
        Double dateDiscount  = Double.valueOf(0);
        //(int year, int month, int dayOfMonth, int hour, int minute) {
        if (showing.getStartTime().getDayOfMonth() == 7)
        {
        	//Requirement says "Any movies showing on 7th, you'll get 1$ discount", this does not specify if 7th means, the 7th show of the day, 7th day of the week or 7th day of the month.
        	//If this would have been a real time scenario this ambiguity would have been raised to the Business for clarification before proceeding with development.
        	//But for this moment, I am going to assume 7th as 7th day of the month meaning 7th Oct, 7th Dec and code per that.
        	dateDiscount  = Double.valueOf(1);  // Any movies showing on 7th, you'll get 1$ discount
        	discountList.add(dateDiscount);
        }   
        
        // biggest discount wins
        Double biggestDiscount = Double.valueOf(0);
        
        if(discountList.size()>0)
        {
        	//Stream all the saved discounts and pick the maximum.
        	biggestDiscount = discountList.stream().max(Comparator.comparing(Double::valueOf)).get();
        }
        return biggestDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}