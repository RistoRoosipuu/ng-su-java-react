package su.ng.disease.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import su.ng.disease.entities.GeneralInfoResponseObject;
import su.ng.disease.services.DiseaseService;
import su.ng.disease.services.SymptomService;
import su.ng.disease.wrapper.Body;
import su.ng.disease.wrapper.DiseaseWrapperObject;

import java.util.List;

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

        List<String> findALL = diseaseService.retrieveAllDiseases();

        diseaseWrapperObject.setPossibleDiseases(findALL);

        log.info("Body was: " + body.getPossibleSymptoms());
        return diseaseWrapperObject;
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
