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

    @ManyToMany(mappedBy = "symptoms")
    private Set<Disease> diseases;

    public Symptom(String name) {
        this.name = name;
    }
}
