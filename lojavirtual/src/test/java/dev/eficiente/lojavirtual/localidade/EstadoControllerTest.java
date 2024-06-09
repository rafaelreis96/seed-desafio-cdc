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
class EstadoControllerTest {

    private static final Faker faker = TestUtils.fakerBR();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    private CriarEstadoRequestDto criarEstadoRequestDto;

    private Estado estado;

    private Pais pais;

    @BeforeEach
    void setUp() {
        pais = new Pais(faker.country().name());
        entityManager.persist(pais);

        estado = new Estado(faker.country().countryCode2(), pais);
        entityManager.persist(estado);

        criarEstadoRequestDto = new CriarEstadoRequestDto();
        criarEstadoRequestDto.setNome(faker.country().countryCode2());
        criarEstadoRequestDto.setIdPais(pais.getId());
    }

    @Test
    void deveCriarEstadoComSucesso() throws Exception {
        mockMvc.perform(post("/estados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.toJson(criarEstadoRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveImpedirCadastro_QuandoNomeForInvalido() throws Exception {
        criarEstadoRequestDto.setNome("");

        mockMvc.perform(post("/estados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.toJson(criarEstadoRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveImpedirCadastro_QuandoJaExistirEstadoComMesmoNome() throws Exception {
        criarEstadoRequestDto.setNome(estado.getNome());
        mockMvc.perform(post("/estados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.toJson(criarEstadoRequestDto)))
                .andExpect(status().isBadRequest());
    }

}