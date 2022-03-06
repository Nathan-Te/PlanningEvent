package com.example.planningevent;

public class Evenement {

    private static int cpt_id;
    private int id;
    private String name;
    private int hour;
    private int minute;
    private int nb_people;
    private String date;

    public Evenement(String name, int hour, int minute, int nb_people, String date) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.nb_people = nb_people;
        this.date = date;
        this.id = this.cpt_id;
        this.cpt_id += 1;
    }

    public static int getCpt_id() {
        return cpt_id;
    }

    public static void setCpt_id(int cpt_id) {
        Evenement.cpt_id = cpt_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Evenement(){
        name = "Default";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getNb_people() {
        return nb_people;
    }

    public void setNb_people(int nb_people) {
        this.nb_people = nb_people;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
