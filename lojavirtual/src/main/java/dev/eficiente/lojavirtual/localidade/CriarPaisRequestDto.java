package dev.eficiente.lojavirtual.localidade;

import dev.eficiente.lojavirtual.validator.uniquevalue.UniqueValue;
import jakarta.validation.constraints.NotBlank;

public class CriarPaisRequestDto {
    @NotBlank
    @UniqueValue(fieldClass = Pais.class, fieldName = "nome")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Pais toModel() {
        return new Pais(nome);
    }

    @Override
    public String toString() {
        return "CriarPaisRequestDto{" +
                "nome='" + nome + '\'' +
                '}';
    }

}
