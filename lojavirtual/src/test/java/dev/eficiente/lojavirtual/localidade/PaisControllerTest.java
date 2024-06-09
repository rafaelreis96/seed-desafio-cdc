package dev.eficiente.lojavirtual.localidade;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import utils.TestUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class PaisControllerTest {

    private static final Faker faker = TestUtils.fakerBR();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    private CriarPaisRequestDto criarPaisRequestDto;

    private Pais pais;

    @BeforeEach
    void setUp() {
        pais = new Pais(faker.country().name());
        entityManager.persist(pais);

        criarPaisRequestDto = new CriarPaisRequestDto();
        criarPaisRequestDto.setNome(faker.country().name());
    }

    @Test
    void deveCadastrarUmPaisComSucesso() throws Exception {
        mockMvc.perform(post("/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.toJson(criarPaisRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveImpedirCadastro_QuandoNomeForInvalido() throws Exception {
        criarPaisRequestDto.setNome("");

        mockMvc.perform(post("/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.toJson(criarPaisRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveImpedirCadastro_QuandoPaisComMesmoNomeJaExistir() throws Exception {
        pais = new Pais(criarPaisRequestDto.getNome());
        entityManager.persist(pais);

        mockMvc.perform(post("/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.toJson(criarPaisRequestDto)))
                .andExpect(status().isBadRequest());
    }


}