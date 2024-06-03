package dev.eficiente.lojavirtual.livro;

import dev.eficiente.lojavirtual.autor.Autor;
import dev.eficiente.lojavirtual.categoria.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "titulo", nullable = false, unique = true)
    private String titulo;

    @Column(name = "resumo", nullable = false)
    private String resumo;

    @Column(name = "sumario", nullable = false)
    private String sumario;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @Column(name = "paginas", nullable = false)
    private Integer paginas;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Temporal(TemporalType.DATE)
    private Date publicacao;

    @OneToOne
    private Categoria categoria;

    @OneToOne
    private Autor autor;

    public Livro() {}

    public Livro(
            @NotBlank String titulo,
            @NotBlank @Size(max = 500) String resumo,
            @NotBlank String sumario,
            @Min(value = 20) BigDecimal preco,
            @NotNull Integer paginas,
            @NotNull String isbn,
            @NotNull Date publicacao,
            @NotNull Categoria categoria,
            @NotNull Autor autor) {

        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.paginas = paginas;
        this.isbn = isbn;
        this.publicacao = publicacao;
        this.categoria = categoria;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", resumo='" + resumo + '\'' +
                ", sumario='" + sumario + '\'' +
                ", preco=" + preco +
                ", paginas=" + paginas +
                ", isbn='" + isbn + '\'' +
                ", publicacao=" + publicacao +
                ", categoria=" + categoria +
                ", autor=" + autor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
