package dk.thebeerclub.brewhub.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "application_user")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;

}
