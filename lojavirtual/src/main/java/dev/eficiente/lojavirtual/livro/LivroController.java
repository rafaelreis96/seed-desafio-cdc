package dev.eficiente.lojavirtual.livro;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/livros", produces = MediaType.APPLICATION_JSON_VALUE)
public class LivroController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> criar(@RequestBody @Valid LivroRequestDto request) {
        Livro livro = entityManager.merge(request.toModel(entityManager));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(livro.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listar(){
        List<Livro> livros = entityManager.createQuery("SELECT l FROM Livro l", Livro.class).getResultList();
        return ResponseEntity.ok(livros);
    }

}
