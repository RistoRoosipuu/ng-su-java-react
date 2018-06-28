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
import su.ng.disease.entities.Symptom;
import su.ng.disease.repositories.SymptomRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SymptomServiceTest {

    @InjectMocks
    private SymptomService symptomService = new SymptomService();

    @Mock
    private SymptomRepository symptomRepository;

    private Symptom symptom;

    @Before
    public void init() {
        symptom = new Symptom();
        symptom.setId(100L);
        symptom.setName("symptomName");

    }

    @Test
    public void findSymptomByName() {
        when(symptomRepository.findSymptomByName("symptomName")).thenReturn(symptom);

        Symptom symptomByName = symptomService.findSymptomByName("symptomName");
        Assert.assertEquals(symptomByName, symptomByName);
        Mockito.verify(symptomRepository, Mockito.times(1)).findSymptomByName(symptomByName.getName());
        Mockito.verifyNoMoreInteractions(symptomRepository);
    }

    @Test
    public void findOverallSymptomCount() {
        when(symptomRepository.findSymptomCount()).thenReturn(1L);

        Long count = symptomService.findOverallSymptomCount();

        assertThat(count).isEqualTo(1L);
        Mockito.verify(symptomRepository, Mockito.times(1)).findSymptomCount();
        Mockito.verifyNoMoreInteractions(symptomRepository);
    }

    @Test
    public void findAllSymptoms() {
        List<String> symptomsFromRepo = Collections.singletonList(symptom.getName());
        Mockito.when(symptomRepository.findAllSymptoms()).thenReturn(symptomsFromRepo);

        List<String> symptomsFromService = symptomService.findAllSymptoms();

        Assert.assertThat(symptomsFromService, Matchers.hasSize(1));
        Mockito.verify(symptomRepository, Mockito.times(1)).findAllSymptoms();
        Mockito.verifyNoMoreInteractions(symptomRepository);
    }

    @Test
    public void findSymptomsWithTheMostSymptoms() {
    }

    @Test
    public void findAll() {
        Iterable<Symptom> symptomsFromRepo = Collections.singletonList(symptom);
        Mockito.when(symptomRepository.findAll()).thenReturn(symptomsFromRepo);

        List<Symptom> symptomsFromService = symptomService.findAll();

        Assert.assertThat(symptomsFromService, Matchers.hasSize(1));
        Mockito.verify(symptomRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(symptomRepository);
    }
}