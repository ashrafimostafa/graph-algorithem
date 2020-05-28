package com.dev.mostafa.maximummatching.model;

public class NodeDM {
    private String name;
    private float x, y;

    public NodeDM(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
