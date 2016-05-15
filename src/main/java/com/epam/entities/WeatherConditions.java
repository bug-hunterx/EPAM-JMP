package com.epam.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Alexey on 15.05.2016.
 */
@Entity
@Table(name = "WEATHER_CONDITIONS")
public class WeatherConditions {

    @Id
    private Integer code;

    private String label;

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
}
