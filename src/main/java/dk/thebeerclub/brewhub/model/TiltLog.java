package dk.thebeerclub.brewhub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "tilt_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TiltLog {

    @Id
    private String id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @Column(name = "gravity")
    private Integer gravity;

    @Column(name = "temperature")
    private Double temperature;

}
