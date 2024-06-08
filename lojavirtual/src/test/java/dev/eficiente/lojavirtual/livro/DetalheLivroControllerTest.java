package dev.eficiente.lojavirtual.livro;

import dev.eficiente.lojavirtual.autor.Autor;
import dev.eficiente.lojavirtual.categoria.Categoria;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class DetalheLivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    private Livro livro;

    @BeforeEach
    void setUp() {
        Categoria categoria = LivroDataProvider.categoria();
        entityManager.persist(categoria);

        Autor autor = LivroDataProvider.autor();
        entityManager.persist(autor);

        livro = LivroDataProvider.livro();
        livro.setCategoria(categoria);
        livro.setAutor(autor);
        entityManager.persist(livro);
    }

    @Test
    void deveRetornarDetalhesDoLivroComSucesso() throws Exception {
        mockMvc.perform(get("/livros/{id}", livro.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(livro.getId()))
                .andExpect(jsonPath("$.titulo").value(livro.getTitulo()));
    }

    @Test
    void deveRetornar404_QuandoLivroNaoEncontrado() throws Exception {
        mockMvc.perform(get("/livros/{id}", livro.getId()))
                .andExpect(status().isNotFound());
    }
}