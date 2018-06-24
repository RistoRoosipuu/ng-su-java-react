package su.ng.disease.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import su.ng.disease.entities.Disease;

public interface DiseaseRepository extends PagingAndSortingRepository<Disease, Long> {
}
