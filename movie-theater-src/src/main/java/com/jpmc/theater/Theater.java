package com.jpmc.theater;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;


public class Theater {

    LocalDateProvider provider;
    private List<Showing> schedule;

    public Theater(LocalDateProvider provider) {
        this.provider = provider;

        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        schedule = List.of(
            new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
            new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
            new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
            new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
            new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
            new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
            new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
            new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
            new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
        	//This needs to be handled gracefully by first logging the erro stating it was not found.
        	//log the stacktrace for easy debug
        	ex.printStackTrace();
        	//instead of throwing this and breaking the system, we should send this to the UI (here console) withe a meaningful message so they can continue further with other steps.
            //throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        	System.out.println("not able to find any showing for given sequence " + sequence + "Please try reserving for an available Show.");
        	System.out.println("We would suggest the following show based on the current time");
        	sequence = getNextShowing();
        	showing = schedule.get(sequence);
        	
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    private int getNextShowing() {
    	int nextSequence = 0;
    	for(Showing s : schedule)
    	{
			if(s.getStartTime().isAfter(LocalDateTime.of(LocalDate.now(), LocalTime.now())))
			{
				nextSequence = s.getSequenceOfTheDay();
				break;
			}
		}
		return nextSequence;
	}

	public void printSchedule() {
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee())
        );
        System.out.println("===================================================");
    }

    //This is designed to return a json string instead of printing it inline so that it can be reused where required and printed on demand.
    public String jsonSchedule() {
    	String jsonStrForSchedule = StringUtils.EMPTY; 
        ObjectMapper mapper = new ObjectMapper();
        //The java 8 Duration bject requires custom serializers and deserializers. 
        //Object mapper can automate this custmiztion if the module is regitered.
        //com.fasterxml.jackson.datatype.jsr310.JavaTimeModule does this customization.
        mapper.registerModule(new JavaTimeModule());
        try
        {
        	 jsonStrForSchedule = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schedule);  
        	System.out.println(jsonStrForSchedule);
        }  
        catch (IOException e) {  
            e.printStackTrace();  
        }  
        return jsonStrForSchedule;
    }

    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public static void main(String[] args) {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
        System.out.println(theater.jsonSchedule());
    }
}
