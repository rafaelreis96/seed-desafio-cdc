package dev.eficiente.lojavirtual.livro;

import com.github.javafaker.Faker;
import dev.eficiente.lojavirtual.autor.Autor;
import dev.eficiente.lojavirtual.categoria.Categoria;
import utils.TestUtils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class LivroDataProvider {

    private static final Faker faker = TestUtils.fakerBR();

    public static Categoria categoria() {
        return new Categoria(faker.cat().name());
    }

    public static Autor autor() {
        return new Autor(
                faker.book().author(),
                faker.internet().emailAddress(),
                faker.lorem().sentence(10));
    }

    public static Livro livro() {
        return new Livro(
                faker.book().title(),
                faker.lorem().sentence(),
                faker.lorem().paragraph(),
                new BigDecimal(faker.number().numberBetween(50, 100)),
                faker.number().numberBetween(200, 400),
                faker.number().digits(10),
                faker.date().future(100, TimeUnit.DAYS),
                null,
                null);
    }
}
