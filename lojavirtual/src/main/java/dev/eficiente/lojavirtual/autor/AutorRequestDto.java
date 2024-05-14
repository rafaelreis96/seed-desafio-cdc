package dev.eficiente.lojavirtual.autor;

import dev.eficiente.lojavirtual.validator.uniquevalue.UniqueValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public class AutorRequestDto implements Serializable {

    @NotBlank
    private String nome;

    @Email
    @UniqueValue(fieldClass = Autor.class, fieldName = "email")
    private String email;

    @NotBlank
    @Length(max = 400)
    private String descricao;

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

    public Autor toModel() {
       return new Autor(nome, email, descricao);
    }

    @Override
    public String toString() {
        return "AutorRequest{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
