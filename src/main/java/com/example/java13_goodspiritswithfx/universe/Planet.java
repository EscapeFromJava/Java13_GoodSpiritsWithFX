package com.example.java13_goodspiritswithfx.universe;

public class Planet {
    String name;
    public Tonnel[] tonnels;

    public Planet(){}

    public Planet(String name) {
        this.name = name;
        tonnels= new Tonnel[0];
    }

    @Override
    public String toString() {
        return name;
    }
}
