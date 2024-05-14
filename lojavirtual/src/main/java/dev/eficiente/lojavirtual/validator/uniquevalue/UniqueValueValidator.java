package dev.eficiente.lojavirtual.validator.uniquevalue;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, String> {

    private final EntityManager entityManager;

    private Class<?> fieldClass;
    private String fieldName;

    public UniqueValueValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.fieldClass = constraintAnnotation.fieldClass();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String nome, ConstraintValidatorContext constraintValidatorContext) {
        if(!StringUtils.hasText(nome)) {
            return false;
        }

        String sql = String.format("select count(*) from %s where %s = :nome", fieldClass.getName(), fieldName);
        Long total = (Long) this.entityManager.createQuery(sql)
                    .setParameter("nome", nome)
                    .getSingleResult();

        return  (total != null && total == 0);
    }
}
