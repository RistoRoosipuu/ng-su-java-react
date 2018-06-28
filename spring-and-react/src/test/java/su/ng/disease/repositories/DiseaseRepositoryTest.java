package su.ng.disease.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import su.ng.disease.entities.Disease;
import su.ng.disease.entities.Symptom;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DiseaseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DiseaseRepository diseaseRepository;


    @Before
    public void setUp() {
        Disease disease = new Disease();
        disease.setName("disease 1");

        Disease disease1 = new Disease();
        disease1.setName("disease 2");

        Disease disease2 = new Disease();
        disease2.setName("disease 3");

        Disease disease3 = new Disease();
        disease3.setName("disease 4");

        Disease disease4 = new Disease();
        disease4.setName("disease 5");

        Symptom symptom = new Symptom("symptom 1");
        Symptom symptom1 = new Symptom("symptom 2");
        Symptom symptom2 = new Symptom("symptom 3");
        Symptom symptom3 = new Symptom("symptom 4");
        Symptom symptom4 = new Symptom("symptom 5");
        Symptom symptom5 = new Symptom("symptom 6");
        Symptom symptom6 = new Symptom("symptom 7");
        Set<Symptom> symptomSet1 = new HashSet<>();
        symptomSet1.add(symptom);
        symptomSet1.add(symptom1);

        Set<Symptom> symptomSet2 = new HashSet<>();
        symptomSet2.add(symptom);
        symptomSet2.add(symptom2);
        symptomSet2.add(symptom3);

        Set<Symptom> symptomSet3 = new HashSet<>();
        symptomSet3.add(symptom4);
        symptomSet3.add(symptom5);
        symptomSet3.add(symptom);
        symptomSet3.add(symptom2);

        Set<Symptom> symptomSet4 = new HashSet<>();
        symptomSet4.add(symptom5);
        symptomSet4.add(symptom);
        symptomSet4.add(symptom2);
        symptomSet4.add(symptom3);
        symptomSet4.add(symptom6);

        disease.setSymptoms(symptomSet1);
        disease1.setSymptoms(symptomSet2);
        disease2.setSymptoms(symptomSet3);
        disease3.setSymptoms(symptomSet4);

        disease4.setSymptoms(symptomSet1);

        entityManager.persist(disease);
        entityManager.persist(disease1);
        entityManager.persist(disease2);
        entityManager.persist(disease3);
        entityManager.persist(disease4);
    }

    @Test
    public void findThreeDiseases_WithHighestSymptomCount() {

        // given from SetUp

        // when
        List<String> threeHighestDiseasesList = diseaseRepository.findThreeDiseases();

        // then
        assertThat(threeHighestDiseasesList.get(0))
                .isEqualTo("disease 4");
        assertThat(threeHighestDiseasesList.get(1))
                .isEqualTo("disease 3");
        assertThat(threeHighestDiseasesList.get(2))
                .isEqualTo("disease 2");
    }

    @Test
    public void findAllDiseases() {
        // when
        List<String> allDiseases = diseaseRepository.findAllDiseases();
        // then
        assertThat(allDiseases.size()).isEqualTo(5);
    }

    @Test
    public void findRandomDisease() {
        // when has to find a random entity
        Disease randomDisease = diseaseRepository.findRandomDisease();
        //then
        assertThat(randomDisease).isNotNull();
    }

    @Test
    public void findDiseaseByName() {

        //when
        Disease disease = diseaseRepository.findDiseaseByName("disease 1");
        //then
        assertThat(disease.getName()).isEqualTo("disease 1");
    }
}