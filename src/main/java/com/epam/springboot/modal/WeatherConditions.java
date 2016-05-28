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
    private static final long serialVersionUID = 1L;

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

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "WeatherConditions{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
