package com.epam.springboot.modal;

import com.epam.springboot.modal.Accidents;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Bill on 2016/5/25.
 */
@Entity
@Table(name="road_surface")
public class RoadConditions {
    @Id
    @Column(name="code")
    private Integer code;
    private String label;
    Accidents aa;
    private Set<Accidents> accidents;

//    @OneToMany(mappedBy = "roadSurfaceConditions", cascade = CascadeType.ALL)
    public Set<Accidents> getAccidents() {
        return accidents;
    }
}
