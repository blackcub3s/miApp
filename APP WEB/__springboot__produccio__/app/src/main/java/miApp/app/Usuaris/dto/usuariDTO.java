package miApp.app.Usuaris.dto;

import miApp.app.utils.validacio.Contrasenya;
import miApp.app.utils.validacio.CorreuElectronic;

public class usuariDTO {

    @CorreuElectronic
    private String correuElectronic;

    @Contrasenya
    private String contrasenya;


    private String alies;


    //private Byte plaSuscripcioActual;
}
