package su.ng.disease.entities;

import lombok.Data;

import java.util.List;

@Data
public class TestObject {

    private Long count;

    private List<String> diseases;
    private List<String> symptoms;

}
