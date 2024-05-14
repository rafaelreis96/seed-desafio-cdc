package dev.eficiente.lojavirtual.categoria;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import utils.TestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaRepository categoriaRepository;

    private Faker faker = TestUtils.fakerBR();

    private CategoriaRequestDto request;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        request = new CategoriaRequestDto();
        request.setNome(faker.book().title());

        categoria = request.toModel();
        categoria.setId(faker.random().nextLong());
    }

    @Test
    void deveCriarUmaNovaCategoria() throws Exception {
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        this.mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtils.toJson(request)))
                .andExpect(status().isCreated());

        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void deveImpedirCriarCategoriaSemNome() throws Exception {
        request.setNome(null);

        when(categoriaRepository.existsByNome(request.getNome())).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        this.mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtils.toJson(request)))
                .andExpect(status().isBadRequest());

        verify(categoriaRepository, never()).existsByNome(request.getNome());
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void deveImpedirCriarCategoriaComDadosInvalidos() throws Exception {
        request.setNome(null);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        this.mockMvc.perform(post("/categorias")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtils.toJson(request)))
                .andExpect(status().isBadRequest());

        verify(categoriaRepository, never()).save(any(Categoria.class));
    }
}