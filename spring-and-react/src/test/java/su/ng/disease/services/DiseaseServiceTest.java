package su.ng.disease.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import su.ng.disease.entities.Disease;
import su.ng.disease.entities.Symptom;
import su.ng.disease.repositories.DiseaseRepository;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiseaseServiceTest {


    @InjectMocks
    private DiseaseService diseaseService = new DiseaseService();

    @Mock
    private DiseaseRepository diseaseRepository;


    @Test
    public void findRandomDisease() {
        Disease disease = new Disease();
        disease.setName("disease 1");

        Symptom symptom = new Symptom("symptom 1");
        Symptom symptom1 = new Symptom("symptom 2");
        Set<Symptom> symptomSet1 = new HashSet<>();
        symptomSet1.add(symptom);
        symptomSet1.add(symptom1);
        disease.setSymptoms(symptomSet1);


        when(diseaseRepository.findRandomDisease()).thenReturn(disease);

        Disease randomDisease = diseaseService.findRandomDisease();

        Assert.assertEquals(disease, randomDisease);
    }

    @Test
    public void findDiseaseByName() {

        Disease disease = new Disease();
        disease.setName("disease 1");

        Symptom symptom = new Symptom("symptom 1");
        Symptom symptom1 = new Symptom("symptom 2");
        Set<Symptom> symptomSet1 = new HashSet<>();
        symptomSet1.add(symptom);
        symptomSet1.add(symptom1);
        disease.setSymptoms(symptomSet1);

        when(diseaseRepository.findDiseaseByName("disease 1")).thenReturn(disease);

        Disease diseaseByName = diseaseService.findDiseaseByName("disease 1");

        Assert.assertEquals(disease, diseaseByName);
    }
}