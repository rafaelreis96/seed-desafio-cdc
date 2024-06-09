package dev.eficiente.lojavirtual.localidade;

import dev.eficiente.lojavirtual.validator.uniquevalue.UniqueValue;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;

public class CriarEstadoRequestDto {

    public static final String MSG_ESTADO_NA0_EXISTE = "O Pais informado não existe";

    @NotBlank
    @UniqueValue(fieldClass = Estado.class, fieldName = "nome")
    private String nome;
    private Long idPais;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdPais() {
        return idPais;
    }

    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

    public Estado toModel(EntityManager entityManager) {
        Pais pais = entityManager.getReference(Pais.class, idPais);
        if(pais == null) {
            throw new IllegalArgumentException(MSG_ESTADO_NA0_EXISTE);
        }

        return new Estado(nome, pais);
    }

    @Override
    public String toString() {
        return "CriarEstadoRequestDto{" +
                "nome='" + nome + '\'' +
                ", idPais=" + idPais +
                '}';
    }
}
