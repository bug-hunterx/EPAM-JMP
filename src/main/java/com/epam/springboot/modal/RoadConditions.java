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
/*
    private Set<Accidents> accidents;

//    @OneToMany(mappedBy = "roadSurfaceConditions", cascade = CascadeType.ALL)
    public Set<Accidents> getAccidents() {
        return accidents;
    }
*/

    public RoadConditions() {}

    public RoadConditions(Integer code, String label) {
        this.code = code;
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
