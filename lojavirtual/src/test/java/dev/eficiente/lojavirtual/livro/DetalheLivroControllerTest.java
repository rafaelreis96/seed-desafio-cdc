package dev.eficiente.lojavirtual.livro;

import com.github.javafaker.Faker;
import dev.eficiente.lojavirtual.autor.Autor;
import dev.eficiente.lojavirtual.categoria.Categoria;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import utils.TestUtils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
class DetalheLivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EntityManager entityManager;

    private Livro livro;

    private final Faker faker = TestUtils.fakerBR();

    @BeforeEach
    void setUp() {
        Categoria categoria = new Categoria(faker.cat().name());
        categoria.setId(1L);

        Autor autor = new Autor(
                faker.book().author(),
                faker.internet().emailAddress(),
                faker.lorem().sentence(10));
        autor.setId(1L);

        livro = new Livro(
                faker.book().title(),
                faker.lorem().sentence(),
                faker.lorem().paragraph(),
                new BigDecimal(faker.number().numberBetween(50, 100)),
                faker.number().numberBetween(200, 400),
                faker.number().digits(10),
                faker.date().future(100, TimeUnit.DAYS),
                categoria,
                autor);
        livro.setId(1L);
    }

    @Test
    void deveRetornarDetalhesDoLivroComSucesso() throws Exception {
        when(entityManager.find(Livro.class, 1L)).thenReturn(livro);

        mockMvc.perform(get("/livros/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value(livro.getTitulo()));

        verify(entityManager, times(1)).find(Livro.class, 1L);
    }

    @Test
    void deveRetornar404_QuandoNaoEncontrarLivro() throws Exception {
        when(entityManager.find(Livro.class, 1L)).thenReturn(null);

        mockMvc.perform(get("/livros/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(entityManager, times(1)).find(Livro.class, 1L);
    }
}