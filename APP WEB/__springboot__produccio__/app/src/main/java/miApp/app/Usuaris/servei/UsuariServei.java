//NOTA: FALTARA AFEGIR FUNCIONS QUE UTILITZIN USUARI AMPLIAT REPOSITORI

package miApp.app.Usuaris.servei;

import miApp.app.Usuaris.repositori.UsuariAmpliatRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import miApp.app.Usuaris.model.Usuari;
import miApp.app.Usuaris.model.UsuariAmpliat;
import miApp.app.Usuaris.repositori.UsuariRepositori;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


//PAS 3: Implementem lògica de negoci. Això ho fem en els "services". Aqui fem un servei
//       anomenat "ServeiusuariPrePago". Dins el que farem serà crear una funcio que
//       ens miri si un usuari existeix (verificant si el seu correu ha sigut guardat).
//       Aqui injectarem la dependència del Repositori (consultes a bbdd).

@Service
public class UsuariServei {

    //NOTA: Autowired es millor posar-lo en el constructor que en l'atribut i fer l'atribut constant amb final
    private final UsuariRepositori repoUsuari;
    private final UsuariAmpliatRepositori repoUsuariAmpliat;

    @Autowired   //injecció de dependència, portem el repositori per poder usar la funcio trobaUsuariPerCorreu
    public UsuariServei(UsuariRepositori repoUsuari, UsuariAmpliatRepositori repoUsuariAmpliat) {
        this.repoUsuari = repoUsuari;
        this.repoUsuariAmpliat = repoUsuariAmpliat;
    }

    //PRE: el correu electronic que volem buscar a l'entitat o classe Usuari (associada a a la taula mySQL usuari)
    //POST: torna true si l'usuari està registrat (apareix correu a la taula usuari) o false altrament.
    public boolean usuariRegistrat(String eMail) {
        Optional<String> mailUsuari = repoUsuari.trobaStringUsuariPerCorreu(eMail);    //Optional ajuda a manejar el tipus de dades (si no troba res estara buit i si torna algo sera Usuari). va bé per manejar els valors nuls.
        System.out.println(mailUsuari.toString());  //per veure les dades de la persona que ha entrat
        return mailUsuari.isPresent();              //si el mailUsuari isPresent és que l'usuari existeix, o que no altrament.
    }

    //PRE: el correu electronic que volem buscar a l'entitat o classe Usuari (associada a a la taula mySQL usuari)
    //POST: torna true si l'usuari esta registrat i té acces als recursos de la app -qualsevol codi >0-.
    //      Tornara false si no està present
    //      o bé si està present pero no te acces als recursos de la app -codi 0-.
    //      d'Acces de l'usuari es superior estricte a 0 (usuari paga).
    //      Si es 0 tornara false (usuari no paga o no te acces de superuusuari)
    public boolean usuariTeAcces(String eMail) {
        Optional<Byte> judiciAcces = repoUsuari.trobaSiUsuariTeAccesArecursosDePago_PerCorreu(eMail);
        return judiciAcces.isPresent() && judiciAcces.get() > 0;
    }
    //PRE: un hash d'una contrasenya i un correu electornic
    //POST: Si troba els DPS parametres pre a la mateixa fila, aleshores tornara cert. En cas contrari tornara false.
    public boolean contraCoincideix(String hashContra, String email) {
        Optional<String> hashet = repoUsuari.trobaStringHashContraPerCorreu(hashContra, email);    //Optional ajuda a manejar el tipus de dades (si no troba res estara buit i si torna algo sera Usuari). va bé per manejar els valors nuls.
        System.out.println(hashet.toString());  //per veure les dades de la persona que ha entrat
        return hashet.isPresent();
    }


    //PRE: res
    //POST: enter amb el nombre d'usuaris que hi ha a la app.
    public int nreUsuarisApp() {
        return repoUsuari.trobaTotsElsCorreusDelsUsuarisRegistrats().size();
    }


    //AQUESTA FUNCIO NO TE CORRESPONDENCIA EN CONTROLADOR PERQUÈ NO VOLEM EXPOSAR A ENDPOINTS RES PQ NO HA D'ANAR AL FRONT NI REBRE RES DEL FRONT.
    //(AIXO TINDRÀ MÉS SENTIT FER-HO EN UN ALTRE PROJECTE SPRING... PERQUE TE A VEURE AMB PROMO I MARKETING)
    //pre: res
    //POST: llista de strings amb els correus electronics que hi ha a la APP.
     public List<String> correusUsuarisApp() {
        return repoUsuari.trobaTotsElsCorreusDelsUsuarisRegistrats();
    }

    public void imprimirUsuarisPerPantalla() {
        Usuari[] llistatUsuaris= repoUsuari.trobaTotsElsUsuarisRegistrats();
        for (int i = 0; i < llistatUsuaris.length; ++i) {
            Usuari u = llistatUsuaris[i];
            System.out.println(u.toString());
        }
    }




    //OPERACIONS PER GUARDAR

    //pre: l'usuari NO existeix a la taula usuari.
    //post: l'usuari queda afegit a la base de dades si retorna true.
    //      si la query de guardat fallés i no el pogués afegir aleshores reotrnaria false
    public boolean afegirUsuari(String correuElectronic, String hashContrasenya, String alies, Byte plaSuscripcioActual) {
        try {
            Usuari nouUsuari = new Usuari();
            nouUsuari.setCorreuElectronic(correuElectronic);
            nouUsuari.setHashContrasenya(hashContrasenya);
            nouUsuari.setAlies(alies);
            nouUsuari.setPlaSuscripcioActual(plaSuscripcioActual);

            // Attempt to save the user to the database
            repoUsuari.save(nouUsuari);

            return true; // guardat dusuari exitos
        } catch (Exception e) {
            // SI HI HA ERRORS DE CONNEXIÍ PER EXEMPLE, DONARA ERROR I RETORNARA EL BOLLEA FALSE
            System.err.println("Error saving user: " + e.getMessage());
            return false; // Save operation failed
        }
    }


    //PRE: existeix JA un usuari a la taula usuari amb id_usuari idèntic al paràmetre idUsuari.
    //POST: la taula usuari_ampliat contindrà una nova fila (si no existeix cap registre amb identificador "idUsuari")
    //      o s'actualitzarà la fila actual (si ja existeix un usuariAmpliat amb id idUsuari).
    //
    //      NOTA: SI NO ES COMPLIS LA CPRECONDICIO D'EXISTÈNCIA DE un Usuari a la taula pare (Usuari)
    //      amb id_usuari igual a idUsuari aleshores questa funcio imprimiria per pantalla que no s'ha pogut guardar
    //      i no s'alteraria la taula usuari_ampliat.
    public void afegirOactualitzarUsuariAmpliat(Integer idUsuari, String nom, String primerCognom, String segonCognom) {
        // Trobo si l'usuari (de la taula pare) existeix! Ha d'existir sempre primer!
        Integer idUsuariRecuperat = repoUsuari.trobaId(idUsuari);
        if (!Objects.equals(idUsuariRecuperat, idUsuari)) {
            System.out.println("Usuari no és a la taula pare! Compte! No s'ha afegit a la taula filla UsuariAmpliat!");
        }
        else {
            //si som aquí és que hi ha usuari. L'usuari existeix i per tant podem procedir a afegir usuariampliat
            UsuariAmpliat nouUsuariAmpliat = new UsuariAmpliat();
            nouUsuariAmpliat.setIdUsuari(idUsuari); // Aquesta clau de usuariAmpliat queda mapejada a la clau de usuari (com al codi sql)
            nouUsuariAmpliat.setNom(nom);
            nouUsuariAmpliat.setPrimerCognom(primerCognom);
            nouUsuariAmpliat.setSegonCognom(segonCognom);

            // Guardar el usuario ampliado (per primer cop o per actualitzr-lo)
            repoUsuariAmpliat.save(nouUsuariAmpliat);
        }
    }




}
