package com.owlaser.paclist.entity;

public class SecurityAdvise {
    private String severity = null, CVE_id = null, CVE_url = null, GHSA_id = null, GHSA_url = null,
            firstPatchedVersion = null, vulnerableVersionRange = null, name = null, ecosystem = null;

    public SecurityAdvise(String severity, String CVE_id, String CVE_url, String GHSA_id, String GHSA_url, String firstPatchedVersion, String vulnerableVersionRange, String name, String ecosystem) {
        this.severity = severity;
        this.CVE_id = CVE_id;
        this.CVE_url = CVE_url;
        this.GHSA_id = GHSA_id;
        this.GHSA_url = GHSA_url;
        this.firstPatchedVersion = firstPatchedVersion;
        this.vulnerableVersionRange = vulnerableVersionRange;
        this.name = name;
        this.ecosystem = ecosystem;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getCVE_id() {
        return CVE_id;
    }

    public void setCVE_id(String CVE_id) {
        this.CVE_id = CVE_id;
    }

    public String getCVE_url() {
        return CVE_url;
    }

    public void setCVE_url(String CVE_url) {
        this.CVE_url = CVE_url;
    }

    public String getGHSA_id() {
        return GHSA_id;
    }

    public void setGHSA_id(String GHSA_id) {
        this.GHSA_id = GHSA_id;
    }

    public String getGHSA_url() {
        return GHSA_url;
    }

    public void setGHSA_url(String GHSA_url) {
        this.GHSA_url = GHSA_url;
    }

    public String getFirstPatchedVersion() {
        return firstPatchedVersion;
    }

    public void setFirstPatchedVersion(String firstPatchedVersion) {
        this.firstPatchedVersion = firstPatchedVersion;
    }

    public String getVulnerableVersionRange() {
        return vulnerableVersionRange;
    }

    public void setVulnerableVersionRange(String vulnerableVersionRange) {
        this.vulnerableVersionRange = vulnerableVersionRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEcosystem() {
        return ecosystem;
    }

    public void setEcosystem(String ecosystem) {
        this.ecosystem = ecosystem;
    }
}
