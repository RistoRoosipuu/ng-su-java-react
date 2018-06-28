package su.ng.disease.services;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import su.ng.disease.entities.Disease;
import su.ng.disease.entities.Symptom;
import su.ng.disease.repositories.DiseaseRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiseaseServiceTest {


    @InjectMocks
    private DiseaseService diseaseService = new DiseaseService();

    @Mock
    private DiseaseRepository diseaseRepository;

    private Disease disease;

    @Before
    public void init() {
        disease = new Disease();
        disease.setId(100L);
        disease.setName("diseaseName");

    }

    @Test
    public void findAllDiseases() {
        List<String> diseaseFromRepo = Collections.singletonList(disease.getName());
        Mockito.when(diseaseRepository.findAllDiseases()).thenReturn(diseaseFromRepo);

        List<String> diseaseFromService = diseaseService.findAllDiseases();

        Assert.assertThat(diseaseFromService, Matchers.hasSize(1));
        Mockito.verify(diseaseRepository, Mockito.times(1)).findAllDiseases();
        Mockito.verifyNoMoreInteractions(diseaseRepository);
    }

    @Test
    public void findDiseasesWithTheMostSymptoms() {
    }

    @Test
    public void findDiseaseThatDoesNotExist() {
        Mockito.when(diseaseRepository.findDiseaseByName("noName")).thenReturn(null);

        Disease disease = diseaseService.findDiseaseByName("noName");

        Assert.assertNull(disease);
        Mockito.verify(diseaseRepository, Mockito.times(1)).findDiseaseByName(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(diseaseRepository);
    }

    @Test
    public void findRandomDisease() {

        Mockito.when(diseaseRepository.findRandomDisease()).thenReturn(disease);


        Disease disease = diseaseService.findRandomDisease();

        Assert.assertNotNull(disease);
        Mockito.verify(diseaseRepository, Mockito.times(1)).findRandomDisease();
        Mockito.verifyNoMoreInteractions(diseaseRepository);

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


    @Test
    public void retrieveAllDiseases() {
        Iterable<Disease> diseaseFromRepo = Collections.singletonList(disease);
        Mockito.when(diseaseRepository.findAll()).thenReturn(diseaseFromRepo);

        Set<Disease> diseaseFromService = diseaseService.retrieveAllDiseases();

        Assert.assertThat(diseaseFromService, Matchers.hasSize(1));
        Mockito.verify(diseaseRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(diseaseRepository);
    }
}