package su.ng.disease.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import su.ng.disease.entities.Disease;
import su.ng.disease.entities.Symptom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SymptomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private SymptomRepository symptomRepository;

    @Before
    public void setUp() {
        Symptom symptom1 = new Symptom("symptom 1");
        Symptom symptom2 = new Symptom("symptom 2");
        Symptom symptom3 = new Symptom("symptom 3");
        Symptom symptom4 = new Symptom("symptom 4");
        Symptom symptom5 = new Symptom("symptom 5");
        Symptom symptom6 = new Symptom("symptom 6");
        entityManager.persist(symptom1);
        entityManager.persist(symptom2);
        entityManager.persist(symptom3);
        entityManager.persist(symptom4);
        entityManager.persist(symptom5);
        entityManager.persist(symptom6);

        Disease disease = new Disease();
        disease.setName("disease 1");

        Set<Symptom> symptomSet = new HashSet<>();

        Symptom symptom_1 = symptomRepository.findSymptomByName("symptom 1");
        Symptom symptom_2 = symptomRepository.findSymptomByName("symptom 2");
        Symptom symptom_3 = symptomRepository.findSymptomByName("symptom 3");
        Symptom symptom_4 = symptomRepository.findSymptomByName("symptom 4");

        symptomSet.add(symptom_1);
        symptomSet.add(symptom_2);
        symptomSet.add(symptom_3);
        symptomSet.add(symptom_4);
        disease.setSymptoms(symptomSet);
        entityManager.persist(disease);

        Disease disease_one = new Disease();
        disease_one.setName("disease 2");
        Set<Symptom> symptomSet1 = new HashSet<>();
        symptomSet1.add(symptom_1);
        symptomSet1.add(symptom_2);
        symptomSet1.add(symptom_3);

        disease_one.setSymptoms(symptomSet1);

        entityManager.persist(disease_one);

    }

    @Test
    public void findSymptomByName() {

        Symptom foundSymptom = symptomRepository.findSymptomByName("symptom 1");

        assertThat(foundSymptom.getName()).isEqualTo("symptom 1");
    }

    @Test
    public void findSymptomCount() {

        Long count = symptomRepository.findSymptomCount();

        assertThat(count).isEqualTo(4L);
    }

    @Test
    public void findThreeSymptoms() {
        //limit is 3, there are 4 in the test data

        List<String> listOfFoundSymptoms = symptomRepository.findThreeSymptoms();
        List<String> knownNames = new ArrayList<>();
        knownNames.add("symptom 1");
        knownNames.add("symptom 2");
        knownNames.add("symptom 3");
        assertThat(listOfFoundSymptoms.containsAll(knownNames));
    }

    @Test
    public void findAllSymptoms() {

        List<String> listOfFoundSymptoms = symptomRepository.findAllSymptoms();

        assertThat(listOfFoundSymptoms.size()).isEqualTo(6);
    }
}