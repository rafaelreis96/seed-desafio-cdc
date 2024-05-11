package dev.eficiente.lojavirtual.validator.emailunico;

import dev.eficiente.lojavirtual.autor.AutorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class EmailUnicoValidator implements ConstraintValidator <EmailUnico, String> {

    private final AutorRepository autorRepository;

    public EmailUnicoValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    public void initialize(EmailUnico annotation) {
        ConstraintValidator.super.initialize(annotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email é obrigatório para validar a unicidade");
        }

        return !autorRepository.existsByEmail(email);
    }
}
