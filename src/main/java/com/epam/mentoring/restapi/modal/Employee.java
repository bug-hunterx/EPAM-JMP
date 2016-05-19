package com.epam.mentoring.restapi.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Created by pengfrancis on 16/5/15.
 */
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private Long departmentId;
    @Column
    private LocalDate onBoardDate;

    public Employee(){

    }

    public Employee(String name, String code, Long departmentId, LocalDate onBoardDate) {
        this.name = name;
        this.code = code;
        this.departmentId = departmentId;
        this.onBoardDate = onBoardDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDate getOnBoardDate() {
        return onBoardDate;
    }

    public void setOnBoardDate(LocalDate onBoardDate) {
        this.onBoardDate = onBoardDate;
    }
}
