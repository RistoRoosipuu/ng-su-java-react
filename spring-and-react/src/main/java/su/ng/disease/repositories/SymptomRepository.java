package su.ng.disease.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import su.ng.disease.entities.Symptom;

public interface SymptomRepository extends PagingAndSortingRepository<Symptom, Long> {

    Symptom findSymptomByName(String name);
}
