package su.ng.disease.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import su.ng.disease.entities.Disease;
import su.ng.disease.entities.GeneralInfoResponseObject;
import su.ng.disease.entities.Symptom;
import su.ng.disease.services.DiseaseService;
import su.ng.disease.services.SymptomService;
import su.ng.disease.wrapper.Body;
import su.ng.disease.wrapper.DiseaseWrapperObject;

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
    public DiseaseWrapperObject getPossibleDisease(@RequestBody Body body) {
        DiseaseWrapperObject diseaseWrapperObject = new DiseaseWrapperObject();

        List<String> symptomsFromBody = new ArrayList<>();

        for (String string : body.getPossibleSymptoms()) {
            if (!string.equals("")) {
                symptomsFromBody.add(string);

                //removed trim from string
            }
        }
        Set<Symptom> fetchedSymptoms = findFromBody(symptomsFromBody);
        for (Symptom symptom : fetchedSymptoms) {
            log.info("We are left with: " + symptom.getName());
        }
        if (symptomsFromBody.isEmpty()) {
            diseaseWrapperObject.setPossibleDiseases(symptomsFromBody);
        } else {
            //List<String> findALL = diseaseService.retrieveAllDiseasesAsString();
            //diseaseWrapperObject.setPossibleDiseases(findALL);

            Set<Disease> diseaseList = diseaseService.retrieveAllDiseases();
            List<String> allpossibleDiseases = new ArrayList<>();
            for (Disease disease : diseaseList) {
                if (disease.getSymptoms().containsAll(fetchedSymptoms)) {
                    log.info("Disease: " + disease.getName() + "contains all the diseases" + disease.getSymptoms().size() + "  and fetched size" + fetchedSymptoms.size());
                    allpossibleDiseases.add(disease.getName());
                }
            }

            diseaseWrapperObject.setPossibleDiseases(allpossibleDiseases);

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

    /*
    Probably better to have one GetMapping that sends away an object that contains all 3
     */

    /**
     * React fetches this faster than it retrieves it. So, react will show 0 until you refresh the page -.-
     *
     * @return
     */
    public Long countUniqueSymptoms() {
        /**
         * select count(distinct symptom_id) from disease_symptoms
         */
        return symptomService.findOverallSymptomCount();
    }

    public List<String> findThreeWithTheMostSymptoms() {

        return diseaseService.findDiseasesWithTheMostSymptoms();
    }

    public List<String> findThreeMostPopularSymptom() {
        return symptomService.findSymptomsWithTheMostSymptoms();
    }


}
