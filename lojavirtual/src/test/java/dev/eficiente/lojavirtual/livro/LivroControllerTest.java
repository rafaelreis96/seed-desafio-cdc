package dev.eficiente.lojavirtual.livro;

import com.github.javafaker.Faker;
import dev.eficiente.lojavirtual.autor.Autor;
import dev.eficiente.lojavirtual.categoria.Categoria;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    private LivroRequestDto livroRequestDto;
    private Livro livro;
    private Categoria categoria;
    private Autor autor;
    private static final Faker faker = TestUtils.fakerBR();

    @BeforeEach
    void setUp() {
        categoria = LivroDataProvider.categoria();
        entityManager.persist(categoria);

        autor = LivroDataProvider.autor();
        entityManager.persist(autor);

        livro = LivroDataProvider.livro();
        livro.setCategoria(categoria);
        livro.setAutor(autor);
        entityManager.persist(livro);

        livroRequestDto = new LivroRequestDto();
        livroRequestDto.setTitulo(faker.book().title());
        livroRequestDto.setResumo(faker.lorem().sentence());
        livroRequestDto.setSumario(faker.lorem().paragraph());
        livroRequestDto.setPreco(new BigDecimal(faker.number().numberBetween(50, 100)));
        livroRequestDto.setPaginas(faker.number().numberBetween(200, 400));
        livroRequestDto.setIsbn(faker.number().digits(10));
        livroRequestDto.setPublicacao(faker.date().future(100, TimeUnit.DAYS));
        livroRequestDto.setIdAutor(autor.getId());
        livroRequestDto.setIdCategoria(categoria.getId());
    }




    @Test
    void deveCriarLivroComSucesso() throws Exception {
        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtils.toJson(livroRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveEvitarCriarLivro_QuandoTituloForVazio() throws Exception {
        livroRequestDto.setTitulo("");

        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtils.toJson(livroRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveListarLivros() throws Exception {
        List<Livro> livros= new ArrayList<Livro>();
        for(int i=0; i< 5; i++) {
            categoria = LivroDataProvider.categoria();
            entityManager.persist(categoria);

            autor = LivroDataProvider.autor();
            entityManager.persist(autor);

            livro = LivroDataProvider.livro();
            livro.setCategoria(categoria);
            livro.setAutor(autor);
            entityManager.persist(livro);
            livros.add(livro);
        }

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id", hasItem(livros.get(0).getId().intValue())))
                .andExpect(jsonPath("$.[*].titulo", hasItem(livros.get(0).getTitulo())))
                .andExpect(jsonPath("$.[*].isbn", hasItem(livros.get(0).getIsbn())));

    }

    @Test
    void deveRetornarOsDetalhesDoLivro_QuandoForEncontrado() throws Exception {
        mockMvc.perform(get("/livros/{id}", livro.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("titulo").value(livro.getTitulo()))
                .andExpect(jsonPath("isbn").value(livro.getIsbn()));
    }

    @Test
    void deveRetornarNotFound_QuandoNaoEncontrarLivro() throws Exception {
        mockMvc.perform(get("/livros/{id}", 123321))
                .andExpect(status().isNotFound());
    }

}