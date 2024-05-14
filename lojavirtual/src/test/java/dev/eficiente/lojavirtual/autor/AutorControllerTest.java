package dev.eficiente.lojavirtual.autor;

import com.github.javafaker.Faker;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import utils.TestUtils;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AutorRepository autorRepository;

    private AutorRequestDto autorRequestDto;

    private final Faker faker = TestUtils.fakerBR();

    private AutorRequestDto request;

    private Autor autor;

    @BeforeEach
    void setUp() {

        this.request = new AutorRequestDto();
        request.setNome(faker.name().fullName());
        request.setEmail(faker.internet().emailAddress());
        request.setDescricao(faker.lorem().sentence());

        this.autor = new Autor();
        autor.setId(faker.random().nextLong());
        autor.setNome(request.getNome());
        autor.setEmail(request.getEmail());
        autor.setDescricao(request.getDescricao());
    }

    @Test
    void deveCriarAutorComSucesso() throws Exception {
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        this.mockMvc.perform(post("/autores")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtils.toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        verify(autorRepository, times(1)).save(any(Autor.class));
    }

    @Test
    void deveImpedirCriarAutorComDadosInvalidos() throws Exception {
        request.setEmail(null);
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        this.mockMvc.perform(post("/autores")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtils.toJson(request)))
                .andExpect(status().isBadRequest());

        verify(autorRepository, times(0)).save(any(Autor.class));
    }

}