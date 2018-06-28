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
        log.info("Saving " + disease + " into Repository");
        return diseaseRepository.save(disease);
    }

    public Set<Disease> retrieveAllDiseases() {
        Iterable<Disease> diseaseIterable = diseaseRepository.findAll();
        Set<Disease> diseaseList = new HashSet<>();
        diseaseIterable.forEach(diseaseList::add);
        return diseaseList;
    }

    public List<String> findDiseasesWithTheMostSymptoms() {
        return diseaseRepository.findThreeDiseases();
    }

    public Disease findRandomDisease() {
        return diseaseRepository.findRandomDisease();
    }

    public Disease findDiseaseByName(String symptomShownToUser) {
        return diseaseRepository.findDiseaseByName(symptomShownToUser);
    }

    protected List<String> findAllDiseases() {
        return diseaseRepository.findAllDiseases();
    }
}
