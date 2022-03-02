package com.example.planningevent;

public class Evenement {

    private static int cpt_id;
    private int id;
    private String name;

    public Evenement(String name) {
        this.name = name;
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
}
