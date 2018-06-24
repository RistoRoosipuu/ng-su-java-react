package su.ng.disease.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;


@RestController
@Slf4j
public class UploadFileController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, the time at the server is now " + new Date() + "\n";
    }

    @PostMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        StringBuilder endResult = new StringBuilder("Response from Controller ");
        BufferedReader br;
        try {

            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                String[] tempSplitedLines = line.split(",");
                for(String tempSplits : tempSplitedLines){
                    log.info(tempSplits);
                }
                log.info("LINE ENDED!!!!!!!!!!!1");
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        log.info(endResult.toString());
        return endResult.toString();
    }
}
