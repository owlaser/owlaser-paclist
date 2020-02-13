package com.owlaser.paclist.entity;

public class ChildNode {
    private String child_groupid;
    private String child_artifactid;
    private String url;

    public ChildNode(String child_groupid, String child_artifactid, String url) {
        this.child_groupid = child_groupid;
        this.child_artifactid = child_artifactid;
        this.url = url;
    }

    public String getChild_groupid() {
        return child_groupid;
    }

    public void setChild_groupid(String child_groupid) {
        this.child_groupid = child_groupid;
    }

    public String getChild_artifactid() {
        return child_artifactid;
    }

    public void setChild_artifactid(String child_artifactid) {
        this.child_artifactid = child_artifactid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
