package com.ecole221.webflux.classe.service.webflux;


import com.ecole221.classe.service.webflux.repositories.ClasseRepository;
import com.ecole221.classe.service.webflux.repositories.FiliereRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.ecole221.classe.service.webflux")
public class ClasseTestConfiguration {

    @Bean
    public ClasseRepository classeRepositoryTest() {
        return Mockito.mock(ClasseRepository.class);
    }

}
