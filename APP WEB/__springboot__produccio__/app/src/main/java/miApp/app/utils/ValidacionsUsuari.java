package miApp.app.utils;

//VALIDO ELS CAMPS DE LA TAULA USUARI
public class ValidacionsUsuari {

    //validació camp contrasenya (TAMBÉ CAL POSAR el not null i el size min=8 i max=25, no te n'oblidis),
    public static final String REGEX_CONTRASENYA =  "^(?=[a-zA-Z0-9àéèíòóú_.ÀÉÈÍÒÓÚñÑ]+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{8,25}$";
    public static final String MISSATGE_ERROR_CONTRASENYA = "¡La contraseña debe contener al menos una minúscula, una mayúscula, un número y no puede contener espacios! Se admiten puntos y barras bajas pero no otros caracteres";
}
