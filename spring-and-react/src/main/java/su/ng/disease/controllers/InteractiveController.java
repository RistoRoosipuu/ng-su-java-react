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

        return wrapper;
    }

    @PostMapping(value = "/agree")
    public InteractiveStatusWrapper hasSymptomAgree(@RequestBody InteractiveStatusWrapper wrapper) {

        log.info("Agree DiseaseWrapperRequestObject is ok: " + wrapper.getDiseaseName());
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

                return agreeContainedSymptom(returnObject, wrapper);
            } else {
                returnObject = wrapper;
                String symptomChanged = findRandomSymptom(wrapper.getAllSymptoms());
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

        log.info("Deny DiseaseWrapperRequestObject is ok: " + wrapper.getDiseaseName());
        InteractiveStatusWrapper returnObject;

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
                returnObject = wrapper;
                String symptomChanged = findRandomSymptom(wrapper.getAllSymptoms());
                returnObject.setSymptomShownToUser(symptomChanged);

                return returnObject;
            } else {
                List<String> allSymptoms2 = wrapper.getAllSymptoms();
                allSymptoms2.remove(wrapper.getSymptomShownToUser().trim());
                returnObject = wrapper;
                returnObject.setAllSymptoms(allSymptoms2);
                String symptomChanged = findRandomSymptom(allSymptoms2);
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

    private String findRandomSymptom(List<String> allSymptoms) {
        Random random = new Random();
        return allSymptoms.get((random.nextInt(allSymptoms.size())));


    }

    private List<String> turnSymptomsIntoStringList(Set<Symptom> symptoms) {
        return symptoms.stream().map(Symptom::getName).collect(toList());
    }

    private InteractiveStatusWrapper agreeContainedSymptom(InteractiveStatusWrapper sendBack, InteractiveStatusWrapper arrive) {
        log.info("Object did indeed contain that symptom: " + arrive.getSymptomsChosenCorrectly().size());
        sendBack.setDiseaseName(arrive.getDiseaseName());
        sendBack.setSymptomsItHas(arrive.getSymptomsItHas());
        List<String> symptomsChosenCorrectly = arrive.getSymptomsChosenCorrectly();
        symptomsChosenCorrectly.add(arrive.getSymptomShownToUser());

        sendBack.setSymptomsChosenCorrectly(symptomsChosenCorrectly);

        List<String> allSymptoms2 = arrive.getAllSymptoms();

        allSymptoms2.remove(arrive.getSymptomShownToUser().trim());

        sendBack.setAllSymptoms(allSymptoms2);

        sendBack.setSymptomShownToUser(findRandomSymptom(allSymptoms2));

        log.info("Object now has found:: " + sendBack.getSymptomsChosenCorrectly().size());

        return sendBack;
    }
}