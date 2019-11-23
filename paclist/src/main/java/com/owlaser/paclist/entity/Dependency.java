package com.owlaser.paclist.entity;
import java.util.ArrayList;
public class Dependency {
    private String artifactId;
    private String version;
    private String groupId;
    private ArrayList<String> versionList;
    private ArrayList<Integer> usageList;
    private String popurlarVersion;
    private String latestStableVersion;
    private ArrayList<String> license;

    public Dependency(String groupId,String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public Dependency(){
        artifactId = null;
        version = null;
        groupId = null;
        versionList = null;
        usageList = null;
        popurlarVersion = null;
        latestStableVersion = null;
        license = null;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getGroupId() {
        return groupId;
    }

    public ArrayList<String> getVersionList() {return versionList;}

    public void setVersionList(ArrayList<String> versionList){this.versionList = versionList;}

    public ArrayList<Integer> getusageList() {return usageList;}

    public void setusageList(ArrayList<Integer> usageList){this.usageList = usageList;}

    public String getPopurlarVersion() {
        return popurlarVersion;
    }

    public String getLatestStableVersion() {
        return latestStableVersion;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setPopurlarVersion(String popurlarVersion) {
        this.popurlarVersion = popurlarVersion;
    }

    public void setLatestStableVersion(String latestStableVersion) {
        this.latestStableVersion = latestStableVersion;
    }

    public ArrayList<String> getLicense() {
        return license;
    }

    public void setLicense(ArrayList<String> license) {
        this.license = license;
    }
}
