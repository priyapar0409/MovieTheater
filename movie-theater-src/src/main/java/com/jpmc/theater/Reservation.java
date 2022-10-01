package com.jpmc.theater;

public class Reservation {
    private Customer customer;
    private Showing showing;
    private int audienceCount;

    public Reservation(Customer customer, Showing showing, int audienceCount) {
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }

    //total fee should be calculated fee that should contain movie fee adjusted with applicable discounts
    //supposed to be using calculateFee method instead of getMovieFee which brings back only original ticket price
    public double totalFee() {
        //return showing.getMovieFee() * audienceCount;
    	return showing.calculateFee(audienceCount);
    }
}