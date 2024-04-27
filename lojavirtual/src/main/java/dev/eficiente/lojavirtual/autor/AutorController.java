package dev.eficiente.lojavirtual.autor;

import dev.eficiente.lojavirtual.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "autores", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutorController {

    private final AutorRepository autorRepository;

    public AutorController(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid AutorRequestDto request) {
        Autor autor = autorRepository.save(request.toModel());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(autor.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.ok(autorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscar(@PathVariable Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado."));

        return ResponseEntity.ok(autor);
    }

}
