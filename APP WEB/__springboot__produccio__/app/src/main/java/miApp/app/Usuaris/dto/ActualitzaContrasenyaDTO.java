package miApp.app.Usuaris.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ActualitzaContrasenyaDTO {


    @Size(min = 8, max = 25, message = "¡La contraseña debe tener entre 8 y 25 caracteres!") //també va en tàndem a @valid
    @NotBlank(message = "¡La contraseña no puede estar vacía!")     //@NotBlank VA EN TÀNDEM A @valid dins del paràmetre del controlador de la funció actualitzaContrasenya
    private String hashContrasenya;
}