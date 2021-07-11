package dk.thebeerclub.brewhub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "brew")
@Data
public class Brew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brew_name")
    private String brewName;

    @Column(name = "brew_type")
    private String brewType;

    @Column(name = "created")
    private ZonedDateTime created = ZonedDateTime.now(ZoneId.of("UTC"));

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

    @Column(name = "tilt_ended")
    private ZonedDateTime tiltEnded;

    @Column(name = "target_start_gravity")
    private Integer targetStartGravity;

    @Column(name = "actual_start_gravity")
    private Integer actualStartGravity;

    @Column(name = "actual_end_gravity")
    private Integer actualEndGravity;

    @Column(name = "target_end_gravity")
    private Integer targetEndGravity;

    @JsonManagedReference
    @OneToMany(mappedBy = "brew")
    @OrderBy(value = "index ASC")
    private List<BrewStep> brewSteps;

    @JsonManagedReference
    @OneToMany(mappedBy = "brew")
    @OrderBy(value = "id ASC")
    private List<Image> images;

    @JsonBackReference// fetch=FetchType.LAZY, didn't work. added backref instead.
    @OneToMany(mappedBy = "parentId")
    @OrderBy(value = "timestamp ASC")
    private List<TiltLog> tiltLogList;

}
