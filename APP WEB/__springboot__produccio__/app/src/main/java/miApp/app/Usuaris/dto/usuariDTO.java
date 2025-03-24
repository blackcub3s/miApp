package miApp.app.Usuaris.dto;

import miApp.app.utils.validacio.Contrasenya;
import miApp.app.utils.validacio.CorreuElectronic;

//L'USAREM PER A LA SOLICITUD POST (de modificacio completa de l'usauri) y PUT (de actualitzacio del recurs compplet de l'usuari)
public class usuariDTO {

    @CorreuElectronic
    private String correuElectronic;

    @Contrasenya
    private String contrasenya;


    private String alies;


    private Byte plaSuscripcioActual;
}
