package dev.eficiente.lojavirtual.livro;

import com.github.javafaker.Faker;
import dev.eficiente.lojavirtual.autor.Autor;
import dev.eficiente.lojavirtual.categoria.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import utils.TestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private LivroController livroController;

    private LivroRequestDto livroRequestDto;
    private Livro livro;
    private Categoria categoria;
    private Autor autor;
    private final Faker faker = TestUtils.fakerBR();


    @BeforeEach
    void setUp() {
        categoria = new Categoria(faker.cat().name());
        categoria.setId(1L);

        autor = new Autor(
                faker.book().author(),
                faker.internet().emailAddress(),
                faker.lorem().sentence(10));
        autor.setId(1L);

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

        livro = new Livro(
                livroRequestDto.getTitulo(),
                livroRequestDto.getResumo(),
                livroRequestDto.getSumario(),
                livroRequestDto.getPreco(),
                livroRequestDto.getPaginas(),
                livroRequestDto.getIsbn(),
                livroRequestDto.getPublicacao(),
                categoria,
                autor);
        livro.setId(1L);

    }

    @Test
    void deveCriarLivroComSucesso() throws Exception {
        when(entityManager.find(Categoria.class, 1L)).thenReturn(categoria);
        when(entityManager.find(Autor.class, 1L)).thenReturn(autor);
        when(entityManager.merge(any(Livro.class))).thenReturn(livro);

        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtils.toJson(livroRequestDto)))
                .andExpect(status().isCreated());

        verify(entityManager, times(1)).merge(any(Livro.class));
    }

    @Test
    void deveEvitarCriarLivro_QuandoTituloForVazio() throws Exception {
        livroRequestDto.setTitulo("");
        when(entityManager.merge(any(Livro.class))).thenReturn(livro);

        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtils.toJson(livroRequestDto)))
                .andExpect(status().isBadRequest());

        verify(entityManager, never()).merge(any(Livro.class));
    }

    @Test
    void deveListarLivros() throws Exception {
        List<Livro> livros = List.of(livro);
        TypedQuery<Livro> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT l FROM Livro l", Livro.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(livros);

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id", hasItem(livro.getId().intValue())))
                .andExpect(jsonPath("$.[*].titulo", hasItem(livro.getTitulo())))
                .andExpect(jsonPath("$.[*].isbn", hasItem(livro.getIsbn())));

        verify(entityManager, times(1))
                .createQuery("SELECT l FROM Livro l", Livro.class).getResultList();
    }

    @Test
    void deveRetornarOsDetalhesDoLivro_QuandoForEncontrado() throws Exception {
        when(entityManager.find(Livro.class, 1L)).thenReturn(livro);

        mockMvc.perform(get("/livros/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("titulo").value(livro.getTitulo()))
                .andExpect(jsonPath("isbn").value(livro.getIsbn()));

        verify(entityManager, times(1)).find(Livro.class, 1L);
    }

    @Test
    void deveRetornarNotFound_QuandoNaoEncontrarLivro() throws Exception {
        when(entityManager.find(Livro.class, 1L)).thenReturn(livro);

        mockMvc.perform(get("/livros/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(entityManager, times(1)).find(Livro.class, 1L);
    }

}