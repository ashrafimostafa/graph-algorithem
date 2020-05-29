package com.dev.mostafa.maximummatching.model;

public class NodeDM {
    private String name;
    private float x, y;
    private int graphId , id;

    public NodeDM(String name, float x, float y , int graphId) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.graphId = graphId;
    }

    public NodeDM(String name, float x, float y, int graphId, int id) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.graphId = graphId;
        this.id = id;
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

    public int getGraphId() {
        return graphId;
    }

    public int getId() {
        return id;
    }
}
