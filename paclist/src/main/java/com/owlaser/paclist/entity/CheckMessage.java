package com.owlaser.paclist.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckMessage {

    private  String notice;
    private ArrayList<String> license_detail;

    public ArrayList<String> getLicense_detail() {
        return license_detail;
    }

    public void setLicense_detail(ArrayList<String> license_detail) {
        this.license_detail = license_detail;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
