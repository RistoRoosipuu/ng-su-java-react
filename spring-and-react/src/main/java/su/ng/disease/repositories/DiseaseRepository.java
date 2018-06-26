package su.ng.disease.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import su.ng.disease.entities.Disease;

import java.util.List;

public interface DiseaseRepository extends PagingAndSortingRepository<Disease, Long> {

    @Query(value = "    SELECT  DISEASE.name\n" +
            "      FROM DISEASE_SYMPTOMS Inner Join DISEASE \n" +
            "Where DISEASE_SYMPTOMS.DISEASE_ID = DISEASE.ID \n" +
            "     GROUP BY DISEASE_ID  \n" +
            "     ORDER BY COUNT(SYMPTOM_ID) DESC, DISEASE.name ASC  LIMIT 3", nativeQuery = true)
    List<String> findThreeDiseases();

    @Query(value = "SELECT NAME FROM DISEASE GROUP BY ID", nativeQuery = true)
    List<String> findAllDiseases();
}
