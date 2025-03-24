package miApp.app.Usuaris.dto;

import lombok.Getter;
import lombok.Setter;
import miApp.app.utils.validacio.Contrasenya;



// UTILITZEM AQUESTA CLASSE PER VALIDAR L'ENTRADA DE DADES A L'ENDPOINT D'UNA CREACIÓ DE CONTRASENYA
// AMB PATCH (e.g. localhost:8080/api/usuaris/{id}/contrasenya).
// LES ANOTACIONS @Size, @NotBlank, @Pattern (QUE LES HE POSAT TOTES DINS DE @Contrasenya)
// van en tàndem amb l'anotació de @valid dins del paràmetre del controlador de la funció on
// es defineix l'endpoint, la funció actualitzaContrasenya, ja que valida el que entra peer l'endpoint mencionat.
@Getter @Setter
public class ActualitzaContrasenyaDTO {

    //HEM DEFINIT ANOTACIÓ DE VALIDACIÓ PERSONALITZADA (V. utils --> validacio --> Contrasenya.java)
    // (combina NotNull, Size i Pattern)
    @Contrasenya
    private String contrasenya; //COMPTE, QUE A LA BASE DE DADES EL CAMP ES DIU hashContrasneya, perquè guardem el hash. Però en el DTO diem contrasenya, ja que no està hashejada encara.
}