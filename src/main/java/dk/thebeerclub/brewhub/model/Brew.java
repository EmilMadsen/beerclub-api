package dk.thebeerclub.brewhub.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "brew")
@Data
public class Brew {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "brew_name")
    private String brewName;

    @Column(name = "brew_type")
    private String brewType;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "brewsters")
    private String brewsters;

    @Column(name = "location")
    private String location;

    @Column(name = "recipe")
    private String recipe;

    @Column(name = "description")
    private String description;

    @Column(name = "tilt_url")
    private String tiltUrl;

    @Column(name = "target_start_gravity")
    private Integer targetStartGravity;

    @Column(name = "actual_start_gravity")
    private Integer actualStartGravity;

    @Column(name = "actual_end_gravity")
    private Integer actualEndGravity;

    @Column(name = "target_end_gravity")
    private Integer targetEndGravity;

    @JsonManagedReference
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "brew")
    private List<BrewStep> brewSteps;


}
