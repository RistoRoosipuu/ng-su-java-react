package su.ng.disease.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import su.ng.disease.entities.Symptom;

import java.util.List;

public interface SymptomRepository extends PagingAndSortingRepository<Symptom, Long> {

    Symptom findSymptomByName(String name);

    @Query(value = "select count(distinct symptom_id) from disease_symptoms", nativeQuery = true)
    Long findSymptomCount();

    @Query(value = "    SELECT SYMPTOM.NAME \n" +
            "      FROM DISEASE_SYMPTOMS Inner Join SYMPTOM  \n" +
            "Where DISEASE_SYMPTOMS.SYMPTOM_ID  = SYMPTOM.ID\n" +
            "     GROUP BY SYMPTOM.ID \n" +
            "     ORDER BY COUNT(DISEASE_ID) DESC, SYMPTOM.name ASC LIMIT 3", nativeQuery = true)
    List<String> findThreeSymptoms();

    @Query(value = "SELECT SYMPTOM.NAME FROM SYMPTOM GROUP BY ID", nativeQuery = true)
    List<String> findAllSymptoms();
}
