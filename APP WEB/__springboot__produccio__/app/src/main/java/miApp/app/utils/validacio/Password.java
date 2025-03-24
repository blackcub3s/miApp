package miApp.app.utils.validacio;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import miApp.app.utils.validacio.validador.PasswordValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static miApp.app.utils.ValidacionsUsuari.*;
import static miApp.app.utils.ValidacionsUsuari.MISSATGE_ERROR_CONTRASENYA;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
@Size(min = TAMANY_MINIM_CONTRASENYA, max = TAMANY_MAXIM_CONTRASENYA, message = MISSATGE_ERROR_TAMANY) //també va en tàndem a @valid (COMPTE QUE NO CUBREIX EL CAS EN QUE ENTRI null EN COMPTES DE STRING BUIT)
@NotBlank(message = MISSATGE_NOT_BLANK_GENERIC)     //@NotBlank VA EN TÀNDEM A @valid dins del paràmetre del controlador de la funció actualitzaContrasenya
@Pattern(regexp = REGEX_CONTRASENYA, message = MISSATGE_ERROR_CONTRASENYA)
@Constraint(validatedBy = {})
public @interface Password {
    String message() default "La contraseña no es válida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

