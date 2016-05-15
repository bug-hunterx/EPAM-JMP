package com.epam.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by rahul.mujnani on 5/13/2016.
 */
@Entity
@Table(name ="weather_conditions")
public class WeatherCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer code;
    private String label;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "weatherCondition")
    private List<Accident> accidents;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Accident> getAccidents() {
        return accidents;
    }

    public void setAccidents(List<Accident> accidents) {
        this.accidents = accidents;
    }

}
