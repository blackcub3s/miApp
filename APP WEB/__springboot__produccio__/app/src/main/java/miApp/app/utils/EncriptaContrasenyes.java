package miApp.app.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//DOCUMENTACIÓ
//https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html

public class EncriptaContrasenyes {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //per defecte usa 10 rounds de strength (pot ser entre 4 i 31 segons la documentacio).


    //PRE: una contrasenya.
    //POST: el hash de la contrasenya.
    public String hashejaContrasenya(String contrasenya) {
        return passwordEncoder.encode(contrasenya);
    }


    //PRE: una contrasenya plana i una contrasenya hashejada
    //POST: retorna true si la contrasenya plana, un cop hashejada coincideix amb la contrasenyaHash. False altrament.
    public boolean verificaContrasenya(String contrasenyaPlana, String contrasenyaHash) {
        return passwordEncoder.matches(contrasenyaPlana, contrasenyaHash);
    }



    //MAIN PER A TESTEJAR LA CLASE. FUNCIONA PERFECTA.
    /*
    public static void main(String[] args) {
        EncriptaContrasenyes encriptador = new EncriptaContrasenyes();
        String contrasenyaPlaneta = "12345678Mm_.";
        String hash = encriptador.hashejaContrasenya(contrasenyaPlaneta);

        System.out.println("hash: " + hash);
        System.out.println("longitud: "+hash.length()+" caràcters");

        //TESTEJO SI S'HAVIA ENCRIPTAT BÉ
        boolean contrasenyaPlanetaQuadraAmbHash = encriptador.verificaContrasenya(contrasenyaPlaneta, hash);
        if (contrasenyaPlanetaQuadraAmbHash) {
            System.out.println("ES CORRECTA");
        } else {
            System.out.println("ES INCORRECTA");
        }







    }

     */

}
