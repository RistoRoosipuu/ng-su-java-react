package su.ng.disease.wrapper;

import lombok.Data;
import su.ng.disease.entities.Disease;

import java.util.List;

@Data
public class InteractiveStatusWrapper {

    private String symptomShownToUser;
    private String diseaseName;


    private List<String> symptomsItHas;
    private List<String> symptomsChosenCorrectly;
    private List<String> allSymptoms;
    private List<String> tempAllSymptoms;
}
