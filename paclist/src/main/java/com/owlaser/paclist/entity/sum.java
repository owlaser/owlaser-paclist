package com.owlaser.paclist.entity;

import java.util.ArrayList;

public class sum {
    private ArrayList<Dependency> dependenciesList;
    private CheckMessage checkMessage;

    public sum() {
    }

    public sum(ArrayList<Dependency> dependenciesList, CheckMessage checkMessage) {
        this.dependenciesList = dependenciesList;
        this.checkMessage = checkMessage;
    }

    public ArrayList<Dependency> getDependenciesList() {
        return dependenciesList;
    }

    public void setDependenciesList(ArrayList<Dependency> dependenciesList) {
        this.dependenciesList = dependenciesList;
    }

    public CheckMessage getCheckMessage() {
        return checkMessage;
    }

    public void setCheckMessage(CheckMessage checkMessage) {
        this.checkMessage = checkMessage;
    }
}
