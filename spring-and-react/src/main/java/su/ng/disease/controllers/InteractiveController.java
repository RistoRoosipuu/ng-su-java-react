package su.ng.disease.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import su.ng.disease.entities.Disease;
import su.ng.disease.entities.Symptom;
import su.ng.disease.services.DiseaseService;
import su.ng.disease.services.SymptomService;
import su.ng.disease.wrapper.InteractiveStatusWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin
@Slf4j
public class InteractiveController {

    @Autowired
    DiseaseService diseaseService;
    @Autowired
    SymptomService symptomService;

    @GetMapping("/startGame")
    public InteractiveStatusWrapper startGame() {
        InteractiveStatusWrapper wrapper = new InteractiveStatusWrapper();

        //Find Random Disease
        Disease disease = diseaseService.findRandomDisease();
        List<String> allSymptomsInDatabase = symptomService.findAllSymptoms();


        wrapper.setDiseaseName(disease.getName());
        wrapper.setSymptomShownToUser(findRandomSymptom(allSymptomsInDatabase));
        wrapper.setAllSymptoms(allSymptomsInDatabase);
        wrapper.setSymptomsChosenCorrectly(new ArrayList<>());
        wrapper.setSymptomsItHas(turnSymptomsIntoStringList(disease.getSymptoms()));
        wrapper.setTempAllSymptoms(new ArrayList<>());


        return wrapper;
    }


    @PostMapping(value = "/agree")
    public InteractiveStatusWrapper hasSymptomAgree(@RequestBody InteractiveStatusWrapper wrapper) {

        log.info("Agree Body is ok: " + wrapper.getDiseaseName());
        InteractiveStatusWrapper returnObject = new InteractiveStatusWrapper();

        List<String> allSymptoms = wrapper.getSymptomsItHas();
        String symptom = wrapper.getSymptomShownToUser().trim();

        if (wrapper.getSymptomsItHas().size() != wrapper.getSymptomsChosenCorrectly().size()) {
            if (allSymptoms.contains(symptom) && (wrapper.getSymptomsChosenCorrectly().size() + 1) == wrapper.getSymptomsItHas().size()) {
                returnObject = wrapper;
                returnObject.getSymptomsChosenCorrectly().add(symptom.trim());
                returnObject.setSymptomShownToUser("success");
                return returnObject;
            }
            if (allSymptoms.contains(symptom)) {

                log.info("Object did indeed contain that symptom: " + wrapper.getSymptomsChosenCorrectly().size());
                returnObject.setDiseaseName(wrapper.getDiseaseName());
                returnObject.setSymptomsItHas(wrapper.getSymptomsItHas());
                List<String> symptomsChosenCorrectly = wrapper.getSymptomsChosenCorrectly();
                symptomsChosenCorrectly.add(wrapper.getSymptomShownToUser());

                returnObject.setSymptomsChosenCorrectly(symptomsChosenCorrectly);

                List<String> allSymptoms2 = wrapper.getAllSymptoms();

                allSymptoms2.remove(wrapper.getSymptomShownToUser().trim());

                returnObject.setAllSymptoms(allSymptoms2);

                returnObject.setSymptomShownToUser(findRandomSymptom(allSymptoms2));

                log.info("Object now has found:: " + returnObject.getSymptomsChosenCorrectly().size());

                return returnObject;

            } else {

                log.info("Object did not have that symptom " + wrapper.getSymptomShownToUser() + " , fetching a new one");
                log.info("All symptoms it had: " + wrapper.getSymptomsItHas());
                log.info("Symptom you are asking about: " + wrapper.getSymptomShownToUser());
                returnObject = wrapper;


                String symptomChanged = findRandomSymptom(wrapper.getAllSymptoms());
                //returnObject.setSymptomShownToUser(findRandomSymptom(wrapper.getAllSymptoms()));

                log.info("Old Symptom was: " + returnObject.getSymptomShownToUser());
                log.info("New Symptom to look for is: " + symptomChanged);

                returnObject.setSymptomShownToUser(symptomChanged);

                return returnObject;
            }

        } else {
            log.info("Looks like the disease has already been determined");

            returnObject = wrapper;

            returnObject.setSymptomShownToUser("success");

            return returnObject;
        }


    }

    @PostMapping(value = "/deny")
    public InteractiveStatusWrapper hasSymptomDeny(@RequestBody InteractiveStatusWrapper wrapper) {

        log.info("Deny Body is ok: " + wrapper.getDiseaseName());
        InteractiveStatusWrapper returnObject = new InteractiveStatusWrapper();

        List<String> allSymptoms = wrapper.getSymptomsItHas();
        String symptom = wrapper.getSymptomShownToUser().trim();

        if (wrapper.getSymptomsItHas().size() != wrapper.getSymptomsChosenCorrectly().size()) {
            if (allSymptoms.contains(symptom) && (wrapper.getSymptomsChosenCorrectly().size() + 1) == wrapper.getSymptomsItHas().size()) {
                returnObject = wrapper;
                returnObject.getSymptomsChosenCorrectly().add(symptom.trim());
                returnObject.setSymptomShownToUser("success");
                return returnObject;
            }
            if (allSymptoms.contains(symptom)) {
                log.info("Object actually had this object " + wrapper.getSymptomShownToUser() + " ,but i have to fetch a new one");
                log.info("All symptoms it had: " + wrapper.getSymptomsItHas());
                log.info("Symptom you are asking about: " + wrapper.getSymptomShownToUser());
                returnObject = wrapper;


                String symptomChanged = findRandomSymptom(wrapper.getAllSymptoms());
                //returnObject.setSymptomShownToUser(findRandomSymptom(wrapper.getAllSymptoms()));

                log.info("Old Symptom was: " + returnObject.getSymptomShownToUser());
                log.info("New Symptom to look for is: " + symptomChanged);

                returnObject.setSymptomShownToUser(symptomChanged);

                return returnObject;
            } else {

                List<String> allSymptoms2 = wrapper.getAllSymptoms();
                allSymptoms2.remove(wrapper.getSymptomShownToUser().trim());

                returnObject = wrapper;

                returnObject.setAllSymptoms(allSymptoms2);

                String symptomChanged = findRandomSymptom(allSymptoms2);

                returnObject.setSymptomShownToUser(symptomChanged);

                log.info("It did not have this symptom, so removing it from all symptoms");


                return returnObject;

            }

        } else {
            log.info("Looks like the disease has already been determined");

            returnObject = wrapper;

            returnObject.setSymptomShownToUser("success");

            return returnObject;
        }

    }

    private String findRandomSymptom(List<String> allSymptoms) {
        Random random = new Random();
        return allSymptoms.get((random.nextInt(allSymptoms.size())));


    }

    private List<String> turnSymptomsIntoStringList(Set<Symptom> symptoms) {
        return symptoms.stream().map(Symptom::getName).collect(toList());
    }
}