package com.rifalcompany.danspro.api;

public class jobModel {
    private String id;
    private String title;
    private String type;

    private String company;
    private String location;

    public jobModel(String id, String title, String type, String company, String location) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.company = company;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }
}
