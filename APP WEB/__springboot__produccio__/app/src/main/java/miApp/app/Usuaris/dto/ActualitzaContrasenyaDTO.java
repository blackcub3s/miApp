package miApp.app.Usuaris.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// UTILITZEM AQUESTA CLASSE PER VALIDAR L'ENTRADA DE DADES A L'ENDPOINT D'UNA CREACIÓ DE CONTRASENYA
// AMB PATCH (e.g. localhost:8080/api/usuaris/{id}/contrasenya).
@Getter @Setter
public class ActualitzaContrasenyaDTO {

    //LES ANOTACIONS CONTENEN LES VALIDACIONS!!!
    @Size(min = 8, max = 25, message = "¡La contraseña debe tener entre 8 y 25 caracteres!") //també va en tàndem a @valid
    @NotBlank(message = "¡La contraseña no puede estar vacía!")     //@NotBlank VA EN TÀNDEM A @valid dins del paràmetre del controlador de la funció actualitzaContrasenya
    @Pattern(
            regexp = "^(?=[a-zA-Z0-9àéèíòóú_.ÀÉÈÍÒÓÚñÑ]+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,25}$",
            message = "¡La contraseña debe contener al menos una minúscula, una mayúscula, un número y " +
                       "no puede contener espacios! Se admiten puntos y barras bajas pero no otros caracteres"
    )
    private String contrasenya; //COMPTE, QUE A LA BASE DE DADES EL CAMP ES DIU hashContrasneya, perquè guardem el hash. Però en el DTO diem contrasenya, ja que no està hashejada encara.
}