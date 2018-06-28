package su.ng.disease.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import su.ng.disease.entities.Disease;
import su.ng.disease.entities.Symptom;
import su.ng.disease.services.DiseaseService;
import su.ng.disease.services.SymptomService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


@RestController
@CrossOrigin
@Slf4j
public class UploadFileController {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private SymptomService symptomService;

    @GetMapping("/hello")
    public String hello() {
        return "API can be accessed";
    }


    @PostMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        BufferedReader br;
        //Can get the file type like this, thus preventing wrong files from being uploaded.
        String endType = file.getOriginalFilename().split("\\.")[1];

        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                String[] tempSplittedLines = line.split(",");

                Disease disease = new Disease();
                Set<Symptom> symptomSet = new HashSet<>();

                disease.setName(tempSplittedLines[0].trim());

                log.info("New Disease created : " + disease.getName());

                for (int i = 1; i < tempSplittedLines.length; i++) {
                    String tempValue = tempSplittedLines[i].trim();
                    Symptom symptom = symptomService.findSymptomByName(tempValue);

                    if (symptom != null) {
                        symptomSet.add(symptom);
                        log.info("SYMPTOM AS IN THE TABLE " + symptom);
                    } else {
                        Symptom createdSymptom = new Symptom(tempValue);
                        symptomSet.add(createdSymptom);
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


        return "uploadFile Triggered";
    }
}
