package su.ng.disease.wrapper;

import lombok.Data;

import java.util.List;

@Data
public class GeneralInfoResponseObject {

    private Long count;

    private List<String> diseases;
    private List<String> symptoms;

}
