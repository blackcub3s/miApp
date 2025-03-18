//NOTA: FALTARA AFEGIR FUNCIONS QUE FACIN SERVIR LES FUNCIONS USUARI AMPLIAT DE DINS USUARISERVEI.java, segons necessitis

package miApp.app.Usuaris.controlador;


//PAS4: Controlador Rest. Amb aixo exposarem l'endpoint que creem al servei UsuariPrePago. Injectarem
// la dependència del Servei.

import miApp.app.Usuaris.model.Usuari;
import miApp.app.Usuaris.repositori.UsuariRepositori;
import miApp.app.Usuaris.servei.UsuariServei;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


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
    public ResponseEntity<HashMap<String, Object>> verificarUsuari(@RequestBody HashMap<String, String> requestDelBody) {  //@RequestBody es per la solicitud POST d'entrada des del front (la post tambe permet obtenir resposta, passant el mail pel formulari i obtenint el json de reposta no nomes es modificar el servidor ojo amb el lio)

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
    public ResponseEntity<HashMap<String, Object>> verificarUsuariIcontrasenya_perA_logIn(@RequestBody HashMap<String, String> requestDelBody) {  //@RequestBody es per la solicitud POST d'entrada des del front (la post tambe permet obtenir resposta, passant el mail pel formulari i obtenint el json de reposta no nomes es modificar el servidor ojo amb el lio)

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
    public ResponseEntity<HashMap<String, Object>> registraUsuari(@RequestBody HashMap<String, String> requestDelBody) {  //@RequestBody es per la solicitud POST d'entrada des del front (la post tambe permet obtenir resposta, passant el mail pel formulari i obtenint el json de reposta no nomes es modificar el servidor ojo amb el lio)

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
    public ResponseEntity<HashMap<String, Integer>> mostraNreUsuaris() { //ni RequestBody ni RequestParam, perque no hi ha dades d'entrada.
        HashMap<String, Integer> mapJSONlike = new HashMap<>();
        mapJSONlike.put("nreUsuarisRegistrats", serveiUPP.nreUsuarisApp());
        return new ResponseEntity<>(mapJSONlike, HttpStatus.OK);
    }



    //CONTROLADORS PER TROBAR (COMPTE CAL PROTEGIR-LOS!!!)
    //  - nreUsuaris -
    //  - usuaris --> totes les dades de la taula







    //PRE: existeix la bbdd i la taula de usuaris (no hi ha parametres d'entrada)
    //POST: Obtens el nombre d'usuaris que hi ha a la teva aplicacio a l'endpoint.
    @GetMapping("/correusUsuaris")
    public ResponseEntity<List<String>> mostraLlistaUsuaris() { //ni RequestBody ni RequestParam, perque no hi ha dades d'entrada.
        return new ResponseEntity<>(serveiUPP.correusUsuarisApp(), HttpStatus.OK);
    }

    //MOSTRA UNA LLSTA DE TOTS ELS USUARIS AMB TOTES LES DADES DE LA TAULA D'USUARIS
    // (SI NO HI HA USUARIS SEGUEIX MOSTRANT 200 OK, PERÒ RETORNA AL CLIENT
    //  UNA RESPONSE ENTITY: LLISTA BUIDA BÀSICAMENT)
    @GetMapping("/usuaris")
    public ResponseEntity<List<Usuari>> mostraUsuaris() {
        return new ResponseEntity<>(serveiUPP.trobaTotsElsUsuaris(), HttpStatus.OK);
    }

    //METODE PER TROBAR UN USUARI PER ID --------------------------------> LA R DEL CRUD
    @GetMapping("/usuaris/{id}")
    public ResponseEntity<Usuari> obtinguesUsuari(@PathVariable("id") int id) {
        Usuari usuari= serveiUPP.trobaPerId(id);
        if (usuari == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //retorno codi d'error 404 (no trobat)
        }
        return new ResponseEntity<>(usuari, HttpStatus.OK);
    }

    //METODE PER CREAR UN USUARI DIRECTAMENT (CURS SPRING FUNDAMENTALS) --> LA C DEL CRUD [ESTUDIA SUBSTITUIR-LO PER registraUsuari I FER CANVIS EN FRONT]
    //
    //     SI EXISTEIX USUARI --> torno un 409
    //     SI NO EXISTEIX ------> torno un 201 (i creo l'usuari a la bbdd).
    @PostMapping("/usuaris")
    public ResponseEntity<Usuari> creaUsuari(@RequestBody Usuari usuari) {
        Usuari nouUsuari = serveiUPP.guardaUsuari(usuari);
        if (nouUsuari == null) { //si nouUsuari es null es que no es pot afegir perquè ja existeix.
            return new ResponseEntity<>(HttpStatus.CONFLICT); //torno 409 CONFLICT (Correu de la BBDD es unique i s'intenta afegir un correu que ja existeix). És mes informatiu que no pas tornar un 500)
        }
        return new ResponseEntity<>(nouUsuari, HttpStatus.CREATED); // 201 CREATED quan es crea correctament
    }
}















