package su.ng.disease.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import su.ng.disease.entities.Disease;
import su.ng.disease.entities.Symptom;
import su.ng.disease.services.DiseaseService;
import su.ng.disease.services.SymptomService;
import su.ng.disease.wrapper.DiseaseWrapperObject;
import su.ng.disease.wrapper.GeneralInfoResponseObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@Slf4j
public class InformationController {

    @Autowired
    SymptomService symptomService;

    @Autowired
    DiseaseService diseaseService;


    @GetMapping("/generalInfo")
    public GeneralInfoResponseObject getGeneralInfo() {
        GeneralInfoResponseObject generalInfoResponseObject = new GeneralInfoResponseObject();
        generalInfoResponseObject.setCount(countUniqueSymptoms());
        generalInfoResponseObject.setDiseases(findThreeWithTheMostSymptoms());
        generalInfoResponseObject.setSymptoms(findThreeMostPopularSymptom());

        return generalInfoResponseObject;
    }

    @PostMapping(value = "/possibleDiseases")
    public DiseaseWrapperObject getPossibleDisease(@RequestBody DiseaseWrapperObject wrapperObject) {
        DiseaseWrapperObject diseaseWrapperObject = new DiseaseWrapperObject();

        List<String> symptomsFromBody = new ArrayList<>();

        for (String string : wrapperObject.getPossibleDiseases()) {
            if (!string.equals("")) {
                symptomsFromBody.add(string);
            }
        }
        Set<Symptom> fetchedSymptoms = findFromBody(symptomsFromBody);

        if (symptomsFromBody.isEmpty()) {
            symptomsFromBody.add("None found!!");
            diseaseWrapperObject.setPossibleDiseases(symptomsFromBody);
        } else {

            Set<Disease> diseaseList = diseaseService.retrieveAllDiseases();
            List<String> allPossibleDiseases = new ArrayList<>();
            for (Disease disease : diseaseList) {
                if (disease.getSymptoms().containsAll(fetchedSymptoms)) {
                    allPossibleDiseases.add(disease.getName());
                }
            }

            if (allPossibleDiseases.isEmpty()) {
                allPossibleDiseases.add("None found! Spelling mistake?");
            }

            diseaseWrapperObject.setPossibleDiseases(allPossibleDiseases);


        }
        return diseaseWrapperObject;
    }

    private Set<Symptom> findFromBody(List<String> symptomsFromBody) {
        Set<Symptom> fetchedSymptoms = new HashSet<>();

        for (String symptom : symptomsFromBody) {
            fetchedSymptoms.add(symptomService.findSymptomByName(symptom));
        }

        return fetchedSymptoms;

    }


    private Long countUniqueSymptoms() {
        return symptomService.findOverallSymptomCount();
    }

    private List<String> findThreeWithTheMostSymptoms() {

        return diseaseService.findDiseasesWithTheMostSymptoms();
    }

    private List<String> findThreeMostPopularSymptom() {
        return symptomService.findSymptomsWithTheMostSymptoms();
    }

}
