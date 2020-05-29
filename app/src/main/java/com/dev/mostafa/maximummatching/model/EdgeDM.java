package com.dev.mostafa.maximummatching.model;

public class EdgeDM {
    private int id , graphId , startNode , endNode;
    private String name;

    public EdgeDM(int graphId, int startNode, int endNode, String name) {
        this.graphId = graphId;
        this.startNode = startNode;
        this.endNode = endNode;
        this.name = name;
    }

    public EdgeDM(int id, int graphId, int startNode, int endNode, String name) {
        this.id = id;
        this.graphId = graphId;
        this.startNode = startNode;
        this.endNode = endNode;
        this.name = name;
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
}
