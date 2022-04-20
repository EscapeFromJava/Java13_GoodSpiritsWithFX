package com.example.java13_goodspiritswithfx.universe;

import java.util.ArrayList;
public class SpacePath {
    ArrayList<Tonnel> steps = new ArrayList<>();

    public int getTotalCost(){
        int s =0;
        for(Tonnel t:steps)
            s+= t.cost;
        return s;
    }

    @Override
    public String toString() {
        return "SpacePath{" +  steps +" | totalCost = "+getTotalCost()+'}';
    }
    public SpacePath() {}
    public SpacePath(SpacePath x) {
        this.steps = new ArrayList<>(x.steps);
    }
}
