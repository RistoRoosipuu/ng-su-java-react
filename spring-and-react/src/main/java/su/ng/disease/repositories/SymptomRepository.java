package su.ng.disease.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import su.ng.disease.entities.Symptom;

import java.util.List;

public interface SymptomRepository extends PagingAndSortingRepository<Symptom, Long> {

    Symptom findSymptomByName(String name);

    @Query(value = "select count(distinct symptom_id) from disease_symptoms", nativeQuery = true)
    Long findSymptomCount();

    @Query(value = "SELECT NAME FROM SYMPTOM  Order by NUMBER_OF_CONNECTED_DISEASES  DESC, NAME ASC LIMIT 3", nativeQuery = true)
    List<String> findThreeSymptoms();
}
