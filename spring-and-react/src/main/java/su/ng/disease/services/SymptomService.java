package su.ng.disease.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ng.disease.entities.Symptom;
import su.ng.disease.repositories.SymptomRepository;

@Service
@Slf4j
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    public Symptom findSymptomByName(String name){
        return symptomRepository.findSymptomByName(name);
    }

}
