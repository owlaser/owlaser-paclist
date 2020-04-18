package com.owlaser.paclist.entity;

import java.util.ArrayList;

public class Sum_dependency_license {
    private ArrayList<Dependency> dependenciesList;
    private CheckMessage checkMessage;

    public Sum_dependency_license() {
    }

    public Sum_dependency_license(ArrayList<Dependency> dependenciesList, CheckMessage checkMessage) {
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
