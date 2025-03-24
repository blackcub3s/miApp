//NOTA: FALTARA AFEGIR FUNCIONS QUE UTILITZIN USUARI AMPLIAT REPOSITORI

package miApp.app.Usuaris.servei;

import miApp.app.Usuaris.dto.ActualitzaContrasenyaDTO;
import miApp.app.Usuaris.repositori.UsuariAmpliatRepositori;
import miApp.app.utils.EncriptaContrasenyes;
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



    //------------------------------------
    //AFEGIT MENTRE VAIG FENT CURS (CRUD)
    //------------------------------------




    //MOSTRA TOTS ELS USUARIS (SI NO N'HI HA TORNAR AUN ALLISTA BUIDA)
    public List<Usuari> trobaTotsElsUsuaris() {
        return repoUsuari.findAll();
    }

    //SI EL TROBA TORNA L'USUARI. EN CAS CONTRARI RETORNA
    public Optional<Usuari> trobaPerId(int id) {
        Optional<Usuari> usuariOp = repoUsuari.findById(id);
        return usuariOp;
    }


    //CREA NOU USUARI (SI EXISTEIX NO L'ACTUALITZA).
    public Optional<Usuari> guardaUsuari(Usuari usuari) {
        if (repoUsuari.trobaStringUsuariPerCorreu(usuari.getCorreuElectronic()).isPresent()) {
            return Optional.empty(); // Si l'usuari ja existeix, torna un optional buit.
        }
        return Optional.of(repoUsuari.save(usuari)); // Si l'usuari no existeix encara, el guardo i el retorno
    }

    //PRE: un usuari per paràmetre.
    //POST: si usuari existia a BBDD s'esborra i retorna true. En cas contrari, no es fa res, i es retorna false.
    public boolean esborraUsuari(int id) {
        Optional<Usuari> usuariOp = this.trobaPerId(id);
        if (usuariOp.isPresent()) {
            repoUsuari.delete(usuariOp.get());
            return true;
        }
        return false;
    }

    //PRE: un usuari per paràmetre i l'identificador
    //POST: si l'usuari existeix s'actualitza el recurs d'usuari
    //      i es torna l'usuari actualitzat dins un optional. En cas contrari,
    //      no s'actualitza res i es torna l'optional.empty()
    public Optional<Usuari> actualitzaUsuari(Usuari usuari, int id) {
        Optional<Usuari> usuariActualitzatOPCIONAL = this.trobaPerId(id); //si no el troba usuariActualitzatOPCIONAL serà buit
        if (usuariActualitzatOPCIONAL.isEmpty()) {
            return Optional.empty();
        } else {
            Usuari usuariActualitzat = usuariActualitzatOPCIONAL.get();

            //poso les dades de l'usuari que entra per paràmetre al nou usuari actualitzat
            usuariActualitzat.setCorreuElectronic(usuari.getCorreuElectronic()); //poso el nou mail
            usuariActualitzat.setHashContrasenya(usuari.getHashContrasenya());   //poso la nova contra.
            usuariActualitzat.setAlies(usuari.getAlies()); //poso el nou alies
            usuariActualitzat.setPlaSuscripcioActual(usuari.getPlaSuscripcioActual());    //poso el nou pla de suscripcio

            //guardo l'usuari actualitzat i el que ja s'ha guardat el passo al controlador per veure'l
            Usuari usuariActualitzatGUARDAT = repoUsuari.save(usuariActualitzat);
            return Optional.of(usuariActualitzatGUARDAT);
        }
    }





    public Optional<Usuari> actualitzaContrasenya(ActualitzaContrasenyaDTO dto, int id) {
        Optional<Usuari> usuariPreActualitzacio_OPCIONAL = this.trobaPerId(id);
        if (usuariPreActualitzacio_OPCIONAL.isEmpty()) {
            return Optional.empty();
        } else {
            Usuari usuariPreActualitzacio = usuariPreActualitzacio_OPCIONAL.get();

            // Solo actualizamos la contraseña
            if (dto.getContrasenya() != null) {
                String contrasenyaPlaneta = dto.getContrasenya(); //CAL FER-LO AQUI I CANVIAR EXTENSIO DE BBDD A LO QUE CALGUI TENINT EN COMPTE QUE HASHEJARAS UNA CONTASENYA D'ENTRE 8 I 25 CARÀCTERS

                //EncriptaContrasenyes encriptador = new EncriptaContrasenyes();
                //String hashContrasenya = encriptador.hashejaContrasenya(contrasenyaPlaneta); //string de 60 caràcters hashejat

                //usuariPreActualitzacio.setHashContrasenya(hashContrasenya);
                usuariPreActualitzacio.setHashContrasenya(contrasenyaPlaneta);
            }

            // Guardamos el usuario actualizado
            Usuari usuariPostActualitzacio_GUARDAT = repoUsuari.save(usuariPreActualitzacio);
            return Optional.of(usuariPostActualitzacio_GUARDAT);
        }
    }









}
