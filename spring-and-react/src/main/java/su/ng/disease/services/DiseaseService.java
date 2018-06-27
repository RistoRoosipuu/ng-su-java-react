package su.ng.disease.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ng.disease.entities.Disease;
import su.ng.disease.repositories.DiseaseRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class DiseaseService {

    @Autowired
    private DiseaseRepository diseaseRepository;

    public Disease createNewDisease(Disease disease) {

        //Overwrite checkById to --> check by Name
        //If exists, get it, if does not exist, create it.
        log.info("Saving " + disease + " into Repository");
        return diseaseRepository.save(disease);
    }


    public Set<Disease> retrieveAllDiseases() {
        Iterable<Disease> diseaseIterable = diseaseRepository.findAll();
        Set<Disease> diseaseList = new HashSet<>();
        diseaseIterable.forEach(diseaseList::add);
        return diseaseList;
    }

    public List<String> retrieveAllDiseasesAsString() {
        return diseaseRepository.findAllDiseases();
    }

    public List<String> findDiseasesWithTheMostSymptoms() {
        return diseaseRepository.findThreeDiseases();
    }

    /**
     public List<Disease> findAllDiseasesSortedByCountAndName() {
     Iterable<Disease> diseaseIterator = diseaseRepository.findAll();
     List<Disease> diseaseList = new ArrayList<>();
     diseaseIterator.forEach(diseaseList::add);
     diseaseList.sort(comparing(Disease::getNumberOfKnownSymptons).thenComparing(comparing(Disease::getName).reversed()));
     if (diseaseList.size() > 3) {
     List<Disease> diseases = new ArrayList<>();
     for (int i = 1; i <= 3; i++) {
     diseases.add(diseaseList.get(diseaseList.size() - i));
     }
     return diseases;
     } else {
     return diseaseList;
     }
     return null;
     }
     **/
}
