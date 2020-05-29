package com.dev.mostafa.maximummatching.model;

public class EdgeDM {
    private int id , graphId , startNode , endNode;
    private String name;
    private double weight;

    public EdgeDM(int id, int graphId, int startNode, int endNode, String name, double weight) {
        this.id = id;
        this.graphId = graphId;
        this.startNode = startNode;
        this.endNode = endNode;
        this.name = name;
        this.weight = weight;
    }

    public EdgeDM(int graphId, int startNode, int endNode, String name, double weight) {
        this.graphId = graphId;
        this.startNode = startNode;
        this.endNode = endNode;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getGraphId() {
        return graphId;
    }

    public int getStartNode() {
        return startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }
}
