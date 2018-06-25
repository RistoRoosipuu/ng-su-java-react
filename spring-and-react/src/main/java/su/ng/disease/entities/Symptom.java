package su.ng.disease.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long numberOfConnectedDiseases;


    @ManyToMany(mappedBy = "symptoms")
    private Set<Disease> diseases;

    public Symptom(String name) {
        this.name = name;
        //On creation, there must be a connection
        this.numberOfConnectedDiseases = 1L;
    }
}
