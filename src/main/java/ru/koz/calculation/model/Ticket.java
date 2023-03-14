package ru.koz.calculation.model;

public class Ticket {
    private String origin;
    private String destination;
    private String departure_date;
    private String departure_time;
    private String arrival_date;
    private String arrival_time;

    public Ticket() {
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }


    public String getDeparture_date() {
        return departure_date;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public String getArrival_time() {
        return arrival_time;
    }
}
