package su.ng.disease.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = {"diseases"})
@Data
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToMany(mappedBy = "symptoms")
    private Set<Disease> diseases = new HashSet<>();

    public Symptom() {
    }

    public Symptom(String name) {
        this.name = name;
    }

}
