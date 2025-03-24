package miApp.app.utils.validacio;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)


//-------VALIDACIONS DTO Contrasenya--------
@Min(value = 0, message = "plan suscripción debe ser mayor que 0") //asegura que el número sea al menos 0.
@Max(value = 7, message = "plan suscripción debe ser más pequeño que 8") //asegura que el número no sea mayor a 7 (no crec que hi hagi d'haver més de 0 a 7 tipus d'usuaris
//-------FI VALIDACIONS DTO Contrasenya--------
public @interface PlaSuscripcio {
    String message() default "Plan suscripción no válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
