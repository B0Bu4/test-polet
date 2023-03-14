package ru.koz.calculation;

public class Main {
    public static void main(String[] args) {
        String file = "tickets.json";
        String from = "VVO";
        String to = "TLV";
        FlyUtil.showCalculation(file, from, to);
    }
}