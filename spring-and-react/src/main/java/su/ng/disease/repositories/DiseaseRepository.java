package su.ng.disease.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import su.ng.disease.entities.Disease;

import java.util.List;

public interface DiseaseRepository extends PagingAndSortingRepository<Disease, Long> {

    @Query(value = "SELECT NAME FROM DISEASE Order by NUMBER_OF_KNOWN_SYMPTONS DESC, NAME ASC LIMIT 3", nativeQuery = true)
    List<String> findThreeDiseases();
}
