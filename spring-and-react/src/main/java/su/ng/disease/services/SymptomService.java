package su.ng.disease.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.ng.disease.entities.Symptom;
import su.ng.disease.repositories.SymptomRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    public Symptom findSymptomByName(String name) {
        return symptomRepository.findSymptomByName(name);
    }

    public Long findOverallSymptomCount() {
        Long count = symptomRepository.findSymptomCount();
        log.info("Symptom Repository count: " + count);
        return count;
    }

    public List<String> findAllSymptoms() {
        return symptomRepository.findAllSymptoms();
    }

    public List<String> findSymptomsWithTheMostSymptoms() {
        return symptomRepository.findThreeSymptoms();
    }

    protected List<Symptom> findAll() {
        Iterable<Symptom> symptomsIterator = symptomRepository.findAll();
        List<Symptom> symptomList = new ArrayList<>();
        symptomsIterator.forEach(symptomList::add);
        return symptomList;
    }
}
