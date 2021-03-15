package com.dovendev.track.jpa.entities;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TracksResources {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String track_id;
    private String resource_name;
    private String resource_type;
    private Blob resource_data;

    public TracksResources() {}

    public TracksResources(String resource_name, String resource_type, Blob resource_data){
        this.resource_name = resource_name;
        this.resource_type = resource_type;
        this.resource_data = resource_data;
    }

    // getter
    public Long getId() {
        return id;
    }
    public String getResourceName() {
        return resource_name;
    }
    public String getResourceType() {
        return resource_type;
    }
    public Blob getResourceData() {
        return resource_data;
    }

    //setter
    public void setResourceName(String resource_name) {
        this.resource_name =  resource_name;
    }
    public void setResourceType(String resource_type) {
        this.resource_type =  resource_type;
    }
    public void setResourceData(Blob resource_data) {
        this.resource_data =  resource_data;
    }

    @Override
    public String toString() {
        return "Tracks [id=" + id + ", resource_name=" + resource_name + ", resource_type=" + resource_type + "]" + resource_data;
    }
}

