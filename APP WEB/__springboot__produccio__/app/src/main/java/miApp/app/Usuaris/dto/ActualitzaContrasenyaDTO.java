package miApp.app.Usuaris.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//IMPORTO LES VALIDACIONS
import static miApp.app.utils.ValidacionsUsuari.MISSATGE_ERROR_CONTRASENYA;
import static miApp.app.utils.ValidacionsUsuari.REGEX_CONTRASENYA;

// UTILITZEM AQUESTA CLASSE PER VALIDAR L'ENTRADA DE DADES A L'ENDPOINT D'UNA CREACIÓ DE CONTRASENYA
// AMB PATCH (e.g. localhost:8080/api/usuaris/{id}/contrasenya).
// LES ANOTACIONS @Size, @NotBlank, @Pattern... van en tàndem amb l'anotació de
// @valid dins del paràmetre del controlador de la funció on es defineix l'endpoint, la funció actualitzaContrasenya,
// ja que valida el que entra peer l'endpoint mencionat.
@Getter @Setter
public class ActualitzaContrasenyaDTO {

    //LES ANOTACIONS CONTENEN LES VALIDACIONS (QUE POSEM EN utils, validaciosnUsuari)!!!
    @Size(min = 8, max = 25, message = "¡La contraseña debe tener entre 8 y 25 caracteres!") //també va en tàndem a @valid (COMPTE QUE NO CUBREIX EL CAS EN QUE ENTRI null EN COMPTES DE STRING BUIT)
    @NotBlank(message = "¡La contraseña no puede estar vacía, ni ser null! Usa como clave contrasenya en la clave del JSON!")     //@NotBlank VA EN TÀNDEM A @valid dins del paràmetre del controlador de la funció actualitzaContrasenya
    @Pattern(regexp = REGEX_CONTRASENYA, message = MISSATGE_ERROR_CONTRASENYA)
    private String contrasenya; //COMPTE, QUE A LA BASE DE DADES EL CAMP ES DIU hashContrasneya, perquè guardem el hash. Però en el DTO diem contrasenya, ja que no està hashejada encara.
}