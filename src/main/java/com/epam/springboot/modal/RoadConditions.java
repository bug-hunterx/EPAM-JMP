package com.epam.springboot.modal;

import com.epam.springboot.modal.Accidents;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Bill on 2016/5/25.
 */
@Entity
@Table(name="road_surface")
public class RoadConditions  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="code")
    private Integer code;
    @Column(name="label")
    private String label;

//    @OneToMany(mappedBy = "roadSurfaceConditions", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//    private Set<Accidents> accidents;

    protected RoadConditions() {}

    public RoadConditions(Integer code) {
        this.code = code;
    }

    public RoadConditions(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

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

    @Override
    public String toString() {
        return "RoadConditions{" +
                "code=" + code +
                ", label='" + label + '\'' +
                '}';
    }
}
