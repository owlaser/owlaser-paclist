package com.owlaser.paclist.entity;
import java.util.ArrayList;


public class Dependency {
    private String artifact_id;
    private String version;
    private String group_id;
    private ArrayList<String> versionList;
    private ArrayList<Integer> usageList;
    private String popular_version;
    private String stable_version;
   
    private String license;



    private ArrayList<String> licenseList;

    public Dependency(String group_id, String artifact_id, String version) {
        this.group_id = group_id;
        this.artifact_id = artifact_id;
        this.version = version;
    }

    public Dependency(){
        artifact_id = null;
        version = null;
        group_id = null;
        versionList = null;
        usageList = null;
        popular_version = null;
        stable_version = null;
        license = null;
        licenseList = null;
    }

    public String getArtifact_id() {
        return artifact_id;
    }

    public void setArtifact_id(String artifact_id) {
        this.artifact_id = artifact_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public ArrayList<String> getVersionList() {
        return versionList;
    }

    public void setVersionList(ArrayList<String> versionList) {
        this.versionList = versionList;
    }

    public ArrayList<Integer> getUsageList() {
        return usageList;
    }

    public void setUsageList(ArrayList<Integer> usageList) {
        this.usageList = usageList;
    }

    public String getPopular_version() {
        return popular_version;
    }

    public void setPopular_version(String popular_version) {
        this.popular_version = popular_version;
    }

    public String getStable_version() {
        return stable_version;
    }

    public void setStable_version(String stable_version) {
        this.stable_version = stable_version;
    }

   

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public ArrayList<String> getLicenseList() {
        return licenseList;
    }

    public void setLicenseList(ArrayList<String> licenseList) {
        this.licenseList = licenseList;
    }
}
