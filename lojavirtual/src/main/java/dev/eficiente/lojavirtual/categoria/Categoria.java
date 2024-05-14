package dev.eficiente.lojavirtual.categoria;

import jakarta.persistence.*;
import org.springframework.util.Assert;

@Entity
public class Categoria {

    public static final int MAX_LENGTH_NOME = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = MAX_LENGTH_NOME)
    private String nome;

    public Categoria(String nome) {
        Assert.hasLength(nome, "Nome e obrigatório");

        if(nome.length() > MAX_LENGTH_NOME) {
            throw new IllegalArgumentException("Nome deve ter no máximo " + MAX_LENGTH_NOME + " caracteres");
        }

        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
