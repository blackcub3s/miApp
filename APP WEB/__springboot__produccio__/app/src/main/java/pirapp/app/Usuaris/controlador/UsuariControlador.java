//NOTA: FALTARA AFEGIR FUNCIONS QUE FACIN SERVIR LES FUNCIONS USUARI AMPLIAT DE DINS USUARISERVEI.java, segons necessitis

package pirapp.app.Usuaris.controlador;


//PAS4: Controlador Rest. Amb aixo exposarem l'endpoint que creem al servei UsuariPrePago. Injectarem
// la dependència del Servei.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pirapp.app.Usuaris.servei.UsuariServei;

import java.util.HashMap;


@RestController //RETORNA DADES EN LLOC DE VISTES (VISTES ERIA @CONTROLLER)
@RequestMapping("/api")  // Optional: define a common base path for your REST endpoints
public class UsuariControlador {

    private final UsuariServei serveiUPP; //millor fer-ho final

    @Autowired                                              //POSEM ANOTACIÓ D'INJECCIÓ DE DEPENDÈNCIES EN EL CONSTRUCTOR (no en l'atribut, no recomanat) ENCARA QUE A VERSIONS RECENTS DE  SPRING ES FA AUTOMATIC
    public UsuariControlador(UsuariServei serveiUPP) {
        this.serveiUPP = serveiUPP;
    }



    //PRE: Un correu entra pel frontend
    //POST: Un hashmap que es passara per response POST amb {"existeixUsuari":"True", teAccesArecursos:"true"} o false segons sigui el cas
    @CrossOrigin(origins = "http://127.0.0.1:5500") // PERMETO AL FRONTEND DEL VSCODE ENVIAR EL CORREU DEL FORMULARI
    @PostMapping("/usuariExisteix")              //@RequestParam es per a solicitud get (http://localhost:8080/api/usuariExisteix?eMail=santiago.sanchez.sans.44@gmail.com)
    ResponseEntity<HashMap<String, Object>> verificarUsuari(@RequestBody HashMap<String, String> requestDelBody) {  //@RequestBody es per la solicitud POST d'entrada des del front (la post tambe permet obtenir resposta, passant el mail pel formulari i obtenint el json de reposta no nomes es modificar el servidor ojo amb el lio)

        //MIRO SI EL MAIL EXISTEIX A LA TAULA USUARIS (ERGO L'USUARI EXISTEIX)
        String eMail = requestDelBody.get("email");
        boolean existeixUsuari = serveiUPP.usuariRegistrat(eMail);

        //CREEM UN HASHMAP PER TORNAR UN OBJECTE DE TIPUS JSON PER SEGUIR AMB ELS PRINCIPIS REST
        HashMap<String, Object> mapJSONlike = new HashMap<>();
        mapJSONlike.put("existeixUsuari", existeixUsuari); //posem el clau valor al hasmap

        //MIRO SI L'USUARI AMB EL MAI LCORRESPONENT TÉ ACCES ALS RECURSOS DE L'APP (I.E. USUARI QUE PAGA)
        boolean usuariTeAcces = serveiUPP.usuariTeAcces(eMail);
        mapJSONlike.put("teAccesArecursos",usuariTeAcces); /*TEST*/

        /*
            AFEGIR AQUI LATRES MISSATGES AL JSON SI HO NECESSITES
         */
        return new ResponseEntity<>(mapJSONlike, HttpStatus.OK);  //torno la response
    }



    //PRE: Un correu i contrasenya entren pel frontend
    //POST: Un hashmap que es passara per response POST amb {"existeixUsuari":"True", teAccesArecursos: "true", contrasenyaCorrecta:"true"} o false segons sigui el cas
    @CrossOrigin(origins = "http://127.0.0.1:5500") // PERMETO AL FRONTEND DEL VSCODE ENVIAR EL CORREU DEL FORMULARI
    @PostMapping("/usuariContraLogIn")              //@RequestParam es per a solicitud get (http://localhost:8080/api/usuariExisteix?eMail=santiago.sanchez.sans.44@gmail.com)
    ResponseEntity<HashMap<String, Object>> verificarUsuariIcontrasenya_perA_logIn(@RequestBody HashMap<String, String> requestDelBody) {  //@RequestBody es per la solicitud POST d'entrada des del front (la post tambe permet obtenir resposta, passant el mail pel formulari i obtenint el json de reposta no nomes es modificar el servidor ojo amb el lio)

        //MIRO SI EL MAIL EXISTEIX A LA TAULA USUARIS (ERGO L'USUARI EXISTEIX)
        String eMail = requestDelBody.get("email");
        boolean existeixUsuari = serveiUPP.usuariRegistrat(eMail);

        //CREEM UN HASHMAP PER TORNAR UN OBJECTE DE TIPUS JSON PER SEGUIR AMB ELS PRINCIPIS REST
        HashMap<String, Object> mapJSONlike = new HashMap<>();
        mapJSONlike.put("existeixUsuari", existeixUsuari); //posem el clau valor al hashmap

        //MIRO SI L'USUARI AMB EL MAIL CORRESPONENT TÉ ACCES ALS RECURSOS DE L'APP (I.E. USUARI QUE PAGA)
        boolean usuariTeAcces = serveiUPP.usuariTeAcces(eMail);
        mapJSONlike.put("teAccesArecursos",usuariTeAcces); /*TEST*/

        //MIRO SI L'USUARI AMB EL MAIL CORRESPONENT COINCIDEIX EL HASH DE LA CONTRASENYA DE LA BBDD AMB EL HASH DE LA QUE HA POSAT PEL FRONT
        String hashContra = requestDelBody.get("contra");  //FER AQUI EL HASHING que no esta fet encara
        boolean contraCorrecta = serveiUPP.contraCoincideix(hashContra, eMail);
        mapJSONlike.put("contrasenyaCorrecta", contraCorrecta);
        /*
            AFEGIR AQUI LATRES MISSATGES AL JSON SI HO NECESSITES
         */
        return new ResponseEntity<>(mapJSONlike, HttpStatus.OK);  //torno la response
    }




    //PRE: Un correu i contrasenya entraran pel frontend
    //POST: Si l'usuari no existeix a usuari es registraran correu i contrasenya.
    //      Es tornara un hasmap {"usuariRegistrat":"True"} si l'usuari no existeix i s'ha registrat.
    //      Es tornara un hashmap {"usuariRegistrat":"True"} si l'usuari JA existeix i per tant no s'ha registrat.
    @CrossOrigin(origins = "http://127.0.0.1:5500") // PERMETO AL FRONTEND DEL VSCODE ENVIAR EL CORREU DEL FORMULARI
    @PostMapping("/registraUsuari")
    ResponseEntity<HashMap<String, Object>> registraUsuari(@RequestBody HashMap<String, String> requestDelBody) {  //@RequestBody es per la solicitud POST d'entrada des del front (la post tambe permet obtenir resposta, passant el mail pel formulari i obtenint el json de reposta no nomes es modificar el servidor ojo amb el lio)

        //MIRO SI EL MAIL EXISTEIX A LA TAULA USUARIS (ERGO L'USUARI EXISTEIX)
        String eMail = requestDelBody.get("email");
        boolean existiaUsuari = serveiUPP.usuariRegistrat(eMail);

        //CREEM UN HASHMAP PER TORNAR UN OBJECTE DE TIPUS JSON PER SEGUIR AMB ELS PRINCIPIS REST
        HashMap<String, Object> mapJSONlike = new HashMap<>();
        if (existiaUsuari) {
            mapJSONlike.put("existiaUsuari", true); //posem el clau valor al hashmap infromant que no registrem l'usuari obviament (pq ja esta registrat)
            mapJSONlike.put("usuariShaRegistrat", false);
        } else { //L'USUARI N OEXISTIA, ERGO L'AFEGEIXO
            String contrasenya = requestDelBody.get("contra");
            boolean usuariAfegitCorrectament = serveiUPP.afegirUsuari(eMail, contrasenya, "aliesAleatoritzat", (byte) 0);
            mapJSONlike.put("usuariShaRegistrat", usuariAfegitCorrectament); //posem el clau valor al hashmap
            mapJSONlike.put("existiaUsuari", false);
        }
        return new ResponseEntity<>(mapJSONlike, HttpStatus.OK);  //torno la response
    }



















    //PRE: existeix la bbdd i la taula de usuaris (no hi ha parametres d'entrada)
    //POST: Obtens el nombre d'usuaris que hi ha a la teva aplicacio a l'endpoint (NO SE SI SERA UTIL)
    @CrossOrigin(origins = "http://127.0.0.1:5500") // PERMETO AL FRONTEND DEL VSCODE ENVIAR EL CORREU DEL FORMULARI
    @GetMapping("/nreUsuaris")
    ResponseEntity<HashMap<String, Integer>> mostraNreUsuaris() { //ni RequestBody ni RequestParam, perque no hi ha dades d'entrada.
        HashMap<String, Integer> mapJSONlike = new HashMap<>();
        mapJSONlike.put("nreUsuarisRegistrats", serveiUPP.nreUsuarisApp());
        return new ResponseEntity<>(mapJSONlike, HttpStatus.OK);
    }



    //AQUEST CONTROLADOR QUEDA DE MOSTRA PERO NO ES UTIL PERQUE EXPOSARIA ELS MAILS EN UNA SOLICITUD GET
    /*


    //PRE: existeix la bbdd i la taula de usuaris (no hi ha parametres d'entrada)
    //POST: Obtens el nombre d'usuaris que hi ha a la teva aplicacio a l'endpoint (NO SE SI SERA UTIL)
    @CrossOrigin(origins = "http://127.0.0.1:5500") // PERMETO AL FRONTEND DEL VSCODE ENVIAR EL CORREU DEL FORMULARI
    @GetMapping("/llistaCorreusUsuaris")
    ResponseEntity<List<String>> mostraLlistaUsuaris() { //ni RequestBody ni RequestParam, perque no hi ha dades d'entrada.
        return new ResponseEntity<>(serveiUPP.correusUsuarisApp(), HttpStatus.OK);
    }
    */
}















