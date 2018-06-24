package su.ng.disease.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ng.disease.entities.Disease;
import su.ng.disease.repositories.DiseaseRepository;

@Service
@Slf4j
public class DiseaseService {

    @Autowired
    private DiseaseRepository diseaseRepository;

    public Disease createNewDisease(Disease disease){

        //Overwrite checkById to --> check by Name
        //If exists, get it, if does not exist, create it.
        log.info("Saving " + disease + " into Repository");
        return diseaseRepository.save(disease);
    }
}
