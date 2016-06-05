package com.epam.springboot.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by bill on 16-5-28.
 */
@Entity
@Table(name="weather_conditions")
public class WeatherConditions {

    @Id
    @Column(name="code")
    private Integer code;
    @Column(name="label")
    private String label;

    protected WeatherConditions() {
    }

    public WeatherConditions(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public WeatherConditions(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "WeatherConditions{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
