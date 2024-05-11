package dev.eficiente.lojavirtual.autor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.util.Assert;

import java.time.Instant;

@Entity
public class Autor {
    
    public static final int MAX_LENGTH_DESCRICAO = 512;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String email;
    private String descricao;
    private Instant dataCriacao = Instant.now();

    public Autor () {

    }

    public Autor(String nome, String email, String descricao) {
        Assert.hasLength(nome, "Nome e obrigatorio");
        Assert.hasLength(email, "Email e obrigatorio");
        Assert.hasLength(descricao, "Descricao e obrigatorio");

        if(descricao.length() > MAX_LENGTH_DESCRICAO) {
            throw new IllegalArgumentException("Descricao deve conter no maximo "+MAX_LENGTH_DESCRICAO+" caracteres");
        }

        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
