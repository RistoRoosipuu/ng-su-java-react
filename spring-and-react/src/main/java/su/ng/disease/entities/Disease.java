package su.ng.disease.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long numberOfKnownSymptons;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Disease_Symptoms",
            joinColumns = {@JoinColumn(name = "disease_id")},
            inverseJoinColumns = {@JoinColumn(name = "symptom_id")}
    )
    private Set<Symptom> symptoms;
}
