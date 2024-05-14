package dev.eficiente.lojavirtual.categoria;

import dev.eficiente.lojavirtual.validator.uniquevalue.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class CategoriaRequestDto {

    @NotBlank
    @UniqueValue(fieldName = "nome", fieldClass = Categoria.class)
    @Length(max = Categoria.MAX_LENGTH_NOME)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria toModel() {
        return new Categoria(this.nome);
    }

    @Override
    public String toString() {
        return "CategoriaRequestDto{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
