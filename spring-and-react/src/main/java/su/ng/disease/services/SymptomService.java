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

    public Symptom saveSymptom(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    public Long findOverallSymptomCount() {
        Long count = symptomRepository.findSymptomCount();
        log.info("Symptom Repository count: " + count);
        return count;
    }


    public List<String> findSymptomsWithTheMostSymptoms() {
        return symptomRepository.findThreeSymptoms();
    }

    public List<Symptom> findAll() {
        Iterable<Symptom> symptomsIterator = symptomRepository.findAll();
        List<Symptom> symptomList = new ArrayList<>();
        symptomsIterator.forEach(symptomList::add);
        return symptomList;
    }
    /**
     public List<Symptom> findAllSymptomsSortedByCountAndName() {
     Iterable<Symptom> symptomsIterator = symptomRepository.findAll();
     List<Symptom> symptomList = new ArrayList<>();
     symptomsIterator.forEach(symptomList::add);
     symptomList.sort(comparing(Symptom::getNumberOfConnectedDiseases).thenComparing(comparing(Symptom::getName).reversed()));
     if (symptomList.size() > 3) {
     List<Symptom> symptoms = new ArrayList<>();
     for (int i = 1; i <= 3; i++) {
     symptoms.add(symptomList.get(symptomList.size() - i));
     }
     return symptoms;
     } else {
     return symptomList;
     }
     }

     **/
}
