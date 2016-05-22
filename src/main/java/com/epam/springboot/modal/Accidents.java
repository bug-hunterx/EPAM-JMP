package com.epam.springboot.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by bill on 16-5-22.
 */
@Entity
public class Accidents {
    @Id
//    @GeneratedValue
    private String id;

    @Column(name="Police_Force")
    private Integer policeForce;
    @Column(name="Severity")
    private Integer severity;

    protected Accidents() {
    }

    public Accidents(String id, Integer policeForce, Integer severity) {
        this.id = id;
        this.policeForce = policeForce;
        this.severity = severity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPoliceForce() {
        return policeForce;
    }

    public void setPoliceForce(Integer policeForce) {
        this.policeForce = policeForce;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Accidents{" +
                "id='" + id + '\'' +
                ", policeForce=" + policeForce +
                ", severity=" + severity +
                '}';
    }
}
