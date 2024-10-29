package com.ecole221.webflux.classe.service.webflux;

import com.ecole221.classe.service.webflux.repositories.ClasseRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = ClasseTestConfiguration.class, properties = {
        "logging.level.org.springframework.r2dbc=DEBUG"
})
public class ClasseRepositoryTest {

    @Autowired
    private ClasseRepository classeRepository;

    @Test
    public void findByMensualiteRange(){
        classeRepository.findByMensualiteBetween(22000, 27000)
                .doOnNext(classe -> log.info("{}", classe))
                .as(StepVerifier::create)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    public void pageable(){
        classeRepository.findBy(PageRequest.of(0, 2)
                        .withSort(Sort.by("mensualite").ascending()))
                .doOnNext(classe -> log.info("{}", classe))
                .as(StepVerifier::create)
                .assertNext(classe -> Assertions.assertEquals(20000, classe.getMensualite()))
                .assertNext(classe -> Assertions.assertEquals(22000, classe.getMensualite()))
                .expectComplete()
                .verify();
    }

    @Test
    public void getInfosClasses(){
        classeRepository.getInfosClasses()
                .doOnNext(classeData -> log.info("{}", classeData))
                .as(StepVerifier::create)
                //.expectNextCount(5)
                .assertNext(classeData -> Assertions.assertEquals(20000, classeData.mensualite()))
                .assertNext(classeData -> Assertions.assertEquals(22000, classeData.mensualite()))
                .assertNext(classeData -> Assertions.assertEquals(25000, classeData.mensualite()))
                .assertNext(classeData -> Assertions.assertEquals(27000, classeData.mensualite()))
                .assertNext(classeData -> Assertions.assertEquals(30000, classeData.mensualite()))
                .expectComplete()
                .verify();
    }

}
