package ru.koz.calculation;

import com.google.gson.Gson;
import ru.koz.calculation.model.Ticket;
import ru.koz.calculation.model.Tickets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FlyUtil {

    public static void showCalculation(String file, String from, String to) {

        List<Ticket> ticketList;

        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {

            String json = new BufferedReader(reader)
                    .lines()
                    .collect(Collectors.joining());

            Gson gson = new Gson();
            Tickets tickets = gson.fromJson(json, Tickets.class);
            ticketList = tickets.getTickets();

        } catch (IOException e) {
            throw new RuntimeException("ОШИБКА ЧТЕНИЯ ФАЙЛА: json-файл отсутствует или не читается");
        }

        List<Long> flightTimes = new ArrayList<>(100);

        for (Ticket ticket : ticketList) {
            String origin = ticket.getOrigin();
            String destination = ticket.getDestination();
            String departureTime = ticket.getDeparture_time();
            String arrivalTime = ticket.getArrival_time();

            if (origin.equals(from) && destination.equals(to) || origin.equals(to) && destination.equals(from)) {
                LocalDateTime departureDateTime = LocalDateTime.parse(ticket.getDeparture_date() + " " + departureTime, DateTimeFormatter.ofPattern("dd.MM.yy H:mm"));
                LocalDateTime arrivalDateTime = LocalDateTime.parse(ticket.getArrival_date() + " " + arrivalTime, DateTimeFormatter.ofPattern("dd.MM.yy H:mm"));
                Duration duration = Duration.between(departureDateTime, arrivalDateTime);
                flightTimes.add(duration.toMinutes());
            }
        }

        double sum = 0;
        for (Long flightTime : flightTimes) {
            sum += flightTime;
        }

        double average = sum / flightTimes.size();
        System.out.println("Cреднее время полета между городами Владивосток и Тель-Авив: " + average + " минут или " + minToHoursMin(average));

        Collections.sort(flightTimes);
        int index = (int) Math.ceil(0.9 * flightTimes.size() - 1);
        double percentile = flightTimes.get(index);
        System.out.println("90-й процентиль времени полета между городами Владивосток и Тель-Авив: " + percentile + " минут или " + minToHoursMin(percentile));
    }

    private static String minToHoursMin(double min) {
        int res = (int) min;
        return res / 60 + " часов " + res % 60 + " минут(ы)";
    }

}
