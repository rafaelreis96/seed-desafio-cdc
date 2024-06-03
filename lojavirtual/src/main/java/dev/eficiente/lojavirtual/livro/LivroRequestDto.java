package dev.eficiente.lojavirtual.livro;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.eficiente.lojavirtual.autor.Autor;
import dev.eficiente.lojavirtual.categoria.Categoria;
import dev.eficiente.lojavirtual.exception.ResourceNotFoundException;
import dev.eficiente.lojavirtual.validator.uniquevalue.UniqueValue;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

public class LivroRequestDto {

    @NotBlank
    @UniqueValue(fieldClass = Livro.class, fieldName = "titulo")
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String resumo;

    private String sumario;

    @NotNull
    private BigDecimal preco;

    @NotNull
    private Integer paginas;

    @NotBlank
    @UniqueValue(fieldClass = Livro.class, fieldName = "isbn")
    private String isbn;

    @Future
    @JsonFormat(pattern="yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date publicacao;

    @NotNull
    private Long idCategoria;

    @NotNull
    private Long idAutor;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Date publicacao) {
        this.publicacao = publicacao;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public Livro toModel(EntityManager entityManager) {
        Categoria categoria = entityManager.find(Categoria.class, idCategoria);
        if(categoria == null) {
            throw new ResourceNotFoundException("Categoria não existe");
        }

        Autor autor = entityManager.find(Autor.class, idAutor);
        if(autor == null) {
            throw new ResourceNotFoundException("Autor não existe");
        }

        return new Livro(titulo, resumo, sumario, preco, paginas, isbn, publicacao, categoria, autor);
    }

    @Override
    public String toString() {
        return "LivroRequestDto{" +
                "titulo='" + titulo + '\'' +
                ", resumo='" + resumo + '\'' +
                ", sumario='" + sumario + '\'' +
                ", preco=" + preco +
                ", paginas=" + paginas +
                ", isbn='" + isbn + '\'' +
                ", publicacao=" + publicacao +
                ", idCategoria=" + idCategoria +
                ", idAutor=" + idAutor +
                '}';
    }
}