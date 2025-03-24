package miApp.app.utils.validacio;

//VALIDO ELS CAMPS DE LA TAULA USUARI
public class ValidacionsUsuari {

    //validació camp contrasenya (TAMBÉ CAL POSAR el @not null i el size min=8 i max=25, no te n'oblidis),
    public static final String REGEX_CONTRASENYA =  "^(?=[a-zA-Z0-9àéèíòóú_.ÀÉÈÍÒÓÚñÑ]+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,25}$";
    public static final String MISSATGE_ERROR_CONTRASENYA = "¡La contraseña debe contener al menos una minúscula, una mayúscula, un número y no puede contener espacios! Se admiten puntos y barras bajas pero no otros caracteres";

    //validacio contrasenya (@Size)
    public static final int TAMANY_MINIM_CONTRASENYA = 8;
    public static final int TAMANY_MAXIM_CONTRASENYA = 25;
    public static final String MISSATGE_ERROR_TAMANY = "¡La contraseña debe tener entre 8 y 25 caracteres!";


    //MISSATGE GENÈRIC ASSOCIAT A ANOTACIÓ @NOT_BLANK PER LES VALIDACIONS D'USUARI QUE FACIS
    public static final String MISSATGE_NOT_BLANK_GENERIC = "¡El campo no puede estar vacío, ni ser null! Comprueba que hayas usado la clave correcta en el JSON de entrada a la api!";

}


