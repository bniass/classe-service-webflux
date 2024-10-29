package com.ecole221.webflux.classe.service.webflux;

import com.ecole221.classe.service.webflux.models.Filiere;
import com.ecole221.classe.service.webflux.repositories.FiliereRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = FiliereTestConfiguration.class, properties = {
        "logging.level.org.springframework.r2dbc=DEBUG"
})
public class FilereRepositoryTest {

    @Autowired
    private FiliereRepository filiereRepository;


    @Test
    public void findAll(){
        filiereRepository.findAll()
                .doOnNext(f->log.info("{}", f))
                .as(StepVerifier::create)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById(){
        filiereRepository.findById(2L)
                .doOnNext(f->log.info("{}", f))
                .as(StepVerifier::create)
                .assertNext(f-> Assertions.assertEquals("G-ECO", f.getCode()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByIdWthInexistingId(){
        assertThatExceptionOfType(AssertionError.class).
                isThrownBy(() -> StepVerifier.create(filiereRepository.findById(20L))
                .recordWith(() -> null)
                .expectComplete()
                .verify())
                .withMessage("expectation \"recordWith\" failed (expected collection; actual supplied is [null])");;
    }

    @Test
    public void findByIdWthInexistingIdBis(){
        filiereRepository.findById(20L)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByLibelleEndingWith(){
        filiereRepository.findByLibelleStartingWith("Génie")
                .doOnNext(f->log.info("{}", f))
                .as(StepVerifier::create)
                .assertNext(f-> Assertions.assertEquals("GL", f.getCode()))
                .assertNext(f-> Assertions.assertEquals("G-ECO", f.getCode()))
                .expectComplete()
                .verify();
    }

    static long id;

    @Test
    public void insertAndDeleteFiliere(){
        // insert
        var filiere = new Filiere();
        filiere.setCode("MCD");
        filiere.setLibelle("Marketing Communication Digital");

        filiereRepository.save(filiere)
                .doOnNext(f->{
                            id = f.getId();
                            log.info("{}", f);
                })
                .as(StepVerifier::create)
                .assertNext(f-> Assertions.assertNotNull(f.getId()))
                .expectComplete()
                .verify();
        //count
        this.filiereRepository.count()
                .as(StepVerifier::create)
                .expectNext(5L)
                .expectComplete()
                .verify();

        //delete
        filiereRepository.deleteById(id)
                .then(filiereRepository.count())
                .as(StepVerifier::create)
                .expectNext(4L)
                .expectComplete()
                .verify();
    }

    @Test
    public void updateFiliere(){
        filiereRepository.findById(2L)
                .doOnNext(filiere -> filiere.setCode("GE"))
                .flatMap(filiere -> filiereRepository.save(filiere))
                .doOnNext(f->log.info("{}", f))
                .as(StepVerifier::create)
                .assertNext(f-> Assertions.assertEquals("G E", f.getCode()))
                .expectComplete()
                .verify();
    }
}
