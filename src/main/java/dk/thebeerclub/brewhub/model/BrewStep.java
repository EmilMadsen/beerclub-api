package dk.thebeerclub.brewhub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "brew_step")
@Data
public class BrewStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "next_step")
    private Long nextStep;

    @Column(name = "index")
    private Integer index;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "started")
    private ZonedDateTime started;

    @Column(name = "ended")
    private ZonedDateTime ended;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Brew brew;


    public BrewStep() { }

    public BrewStep(Brew brew, String name, Long nextStep, Integer index) {
        this.brew = brew;
        this.name = name;
        this.nextStep = nextStep;
        this.index = index;
    }

}
