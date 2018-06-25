package su.ng.disease.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import su.ng.disease.entities.Disease;
import su.ng.disease.entities.Symptom;
import su.ng.disease.services.DiseaseService;
import su.ng.disease.services.SymptomService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@RestController
@Slf4j
public class UploadFileController {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private SymptomService symptomService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, the time at the server is now " + new Date() + "\n";
    }

    @PostMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        StringBuilder endResult = new StringBuilder("Response from Controller ");
        BufferedReader br;
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                String[] tempSplittedLines = line.split(",");

                Disease disease = new Disease();
                Set<Symptom> symptomSet = new HashSet<>();

                disease.setName(tempSplittedLines[0]);

                for (int i = 1; i < tempSplittedLines.length; i++) {
                    String tempValue = tempSplittedLines[i];
                    Symptom symptom = symptomService.findSymptomByName(tempValue);


                    if (symptom != null) {
                        symptomSet.add(symptom);
                        log.info("SYMPTOM AS IN THE TABLE " + symptom);
                    } else {
                        symptomSet.add(new Symptom(tempValue));
                        log.info("SYMPTOM WAS NOT IN THE TABLE REEEEEEE");
                    }

                }

                disease.setSymptoms(symptomSet);

                diseaseService.createNewDisease(disease);
                log.info("LINE ENDED!!!!!!!!!!!1");
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        log.info(endResult.toString());
        return endResult.toString();
    }
}
