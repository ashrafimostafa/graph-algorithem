package com.dev.mostafa.maximummatching.model;

public class AlgorithmDM {
    private int id;
    private String name , document , pseudoCode;

    public AlgorithmDM(String name, String document, String pseudoCode) {
        this.name = name;
        this.document = document;
        this.pseudoCode = pseudoCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public String getPseudoCode() {
        return pseudoCode;
    }
}
