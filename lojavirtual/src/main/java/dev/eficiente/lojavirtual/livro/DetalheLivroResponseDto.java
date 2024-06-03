package dev.eficiente.lojavirtual.livro;

import dev.eficiente.lojavirtual.autor.Autor;

import java.math.BigDecimal;

public class DetalheLivroResponseDto {
    private Autor auto;
    private String titulo;
    private String isbn;
    private int numeroPaginas;
    private BigDecimal preco;
    private String resumo;
    private String sumario;

    public DetalheLivroResponseDto(Livro livro) {
        this.auto = livro.getAutor();
        this.titulo = livro.getTitulo();
        this.isbn = livro.getIsbn();
        this.numeroPaginas = livro.getPaginas();
        this.preco = livro.getPreco();
        this.resumo = livro.getResumo();
        this.sumario = livro.getSumario();
    }

    public Autor getAuto() {
        return auto;
    }

    public void setAuto(Autor auto) {
        this.auto = auto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
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

    @Override
    public String toString() {
        return "DetalheLivroResponseDto{" +
                "auto=" + auto +
                ", titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", numeroPaginas=" + numeroPaginas +
                ", preco=" + preco +
                ", resumo='" + resumo + '\'' +
                ", sumario='" + sumario + '\'' +
                '}';
    }
}
