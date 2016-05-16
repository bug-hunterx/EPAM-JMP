package com.epam.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="accident_severity")
public class AccidentSeverity {
	
	@Column(name="code")
	private Long code;
	
	@Column(name="label")
	private String label;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "AccidentSeverity [code=" + code + ", label=" + label + "]";
	}
}
