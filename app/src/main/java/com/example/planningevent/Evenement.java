package com.example.planningevent;

public class Evenement {

    private String name;

    public Evenement(String name) {
        this.name = name;
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
