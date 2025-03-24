package miApp.app.Usuaris.dto;

import lombok.Getter;
import lombok.Setter;
import miApp.app.utils.validacio.Alies;
import miApp.app.utils.validacio.Contrasenya;
import miApp.app.utils.validacio.CorreuElectronic;
import miApp.app.utils.validacio.PlaSuscripcio;

//L'USAREM PER A LA SOLICITUD POST (de modificacio completa de l'usauri) y PUT (de actualitzacio del recurs compplet de l'usuari)
@Getter @Setter
public class UsuariDTO {

    @CorreuElectronic
    private String correuElectronic;

    @Contrasenya
    private String contrasenya;

    @Alies
    private String alies;

    @PlaSuscripcio
    private Byte plaSuscripcioActual;
}
