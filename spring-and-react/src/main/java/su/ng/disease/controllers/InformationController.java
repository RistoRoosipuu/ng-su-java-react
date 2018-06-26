package su.ng.disease.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import su.ng.disease.entities.TestObject;
import su.ng.disease.services.DiseaseService;
import su.ng.disease.services.SymptomService;

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
    public TestObject getGeneralInfo() {
        TestObject testObject = new TestObject();

        testObject.setCount(countUniqueSymptoms());
        testObject.setDiseases(findThreeWithTheMostSymptoms());
        testObject.setSymptoms(findThreeMostPopularSymptom());

        /**
         List<String> diseases = findThreeWithTheMostSymptoms().stream()
         .map(d -> (d.getName()))
         .collect(Collectors.toList());
         testObject.setDiseases(diseases);

         List<String> symptoms = findThreeMostPopularSymptom().stream()
         .map(s -> (s.getName()))
         .collect(Collectors.toList());
         testObject.setSymptoms(symptoms);

         testObject.setCount(countUniqueSymptoms());
         **/
        return testObject;
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
