package com.ecole221.webflux.classe.service.webflux;

import com.ecole221.classe.service.webflux.controllers.ClasseController;
import com.ecole221.classe.service.webflux.services.IClasse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(ClasseController.class)
public class ClasseControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private IClasse iClasse;
}
