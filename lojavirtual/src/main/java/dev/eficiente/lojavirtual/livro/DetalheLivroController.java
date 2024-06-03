package dev.eficiente.lojavirtual.livro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class DetalheLivroController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/{id}")
    public ResponseEntity<DetalheLivroResponseDto> detalhe(@PathVariable Long id) {
        Livro livro = entityManager.find(Livro.class, id);
        if (livro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DetalheLivroResponseDto(livro));
    }
}
