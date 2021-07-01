package dk.thebeerclub.brewhub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "image")
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "step_id")
    private Long stepId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created")
    private ZonedDateTime created = ZonedDateTime.now(ZoneId.of("UTC"));

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Brew brew;

}
