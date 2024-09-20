var minConvoc = 2020;   //obtenir de bbdd BBDD
var maxConvoc = 2020;     //obtenir de bbdd BBDD




//localStorage.clear();
//PRE: s es un STRING que pot contenir nombres o altres caracters (., ,, : ....)
//POST: retorna true si tot que hi ha es exclusivament nombres enters
//      i el nombre no comença per zero. False altrament i el nombre no comença.
function esNombre(s) {
    console.log("length s: "+s.length);
    var llistaNombres = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
    var trobadaLletra = false; //boolea que indica si s'ha trobat un caracter no numeric (no compres en llistaNombres)
    var i = 0;
    while (i < s.length && !trobadaLletra) { //cerco
        var j = 0;
        var trobatNre = false; //cerco si s[i] esta dins la llistaNombres
        while (j < llistaNombres.length && !trobatNre) {
            if (s[i] == llistaNombres[j]) {
                trobatNre = true; 
            }
            j = j + 1;
        }
        if (!trobatNre)
            return false; //ja hem trobat una lletra o caracter raro i podem dir que l'string "s" no es numeric

        i = i + 1;
    }
    return true; //si arribem aqui es que es un nombre perque hem recorregut tot l'string "s" i no ha anat al return previ
}


function amaga_Cercador() {
    var formConvocItem = document.getElementsByClassName("contenidorFConvocItem")[0];
    formConvocItem.style.display="none";
}

function elimina_text_previ_cercador() {
    var formulari = document.getElementById("cercaPregunta");
    formulari.reset();
}

function mostra_Cercador() {
    var formConvocItem = document.getElementsByClassName("contenidorFConvocItem")[0];
    formConvocItem.style.display = "flex"; // Set display to flex
    formConvocItem.style.justifyContent = "center"; // Horizontal centering
    formConvocItem.style.alignItems = "center"; // Vertical centering
}   

function amaga_segonaPagina() {
    document.getElementById("contenidorItem2APAGINA").style.display="none";
}          

// FUNCIONS PEL FORMULARI DE LA PREGUNTA


//PRE: - respCorrGS (string) passada com a variable global (respCorrectaGoldStandard de la bbdd o json)
//     - Requereix el formulari de les reguntes (necessita obtenir selectedOption de l'usuari com a query selector)
//POST: - si es correcta posa verd, si es incorrecta posa verd a la opció correcta i vermell a la incorrecta. Si usuari
//        no ha contestat aleshores posa vermell.
function comprovaResposta() {
    var selectedOption = document.querySelector('input[name="grupOpcioPregunta"]:checked');
    console.log("resCorrGS: "+respCorrGS);
    if (selectedOption) {
        console.log("resUsuari: "+selectedOption.value);
        if (selectedOption.value == respCorrGS)
            document.getElementById("etiquetaForm"+respCorrGS).style.color="green";
        else {
            document.getElementById("etiquetaForm"+selectedOption.value).style.color="red";
            document.getElementById("etiquetaForm"+respCorrGS).style.color="green";
        }
    } else {
        document.getElementById("etiquetaForm"+respCorrGS).style.color="orange";
    }




    
       
}

//ELIMINA TOTS ELS FILLS DE L'ELEMENT DIV AMB ID "contnidorFormPregunta" PER DEIXAR-HO LLIURE PER CREAR-NE UN ALTRE
//ES A DIR, PER CREAR UNA ALTRA PREGUNTA AL TORNAR A LA "primera pagina".
function destrueixFormulari() {
    var contenidorFormulari = document.getElementsByClassName("contenidorFormPregunta")[0];
    // Loop through all child nodes and remove them
    while (contenidorFormulari.firstChild) {
        contenidorFormulari.removeChild(contenidorFormulari.firstChild);
    }
}

function eliminaIMG_delLi_area() {
    var contenidorFormulari = document.getElementById("displayImgArea");
    contenidorFormulari.removeChild(contenidorFormulari.firstChild);           
}
//PAGINA 2 --> FUNCIO QUE S'ACTIVA QUAN CLIQUEM EL BOTO DE "BuscaOtroitem", MITJANÇANT UN EVENT LISTENER. 
//             PERMET QUE PUGUEM ANMAR DE NOU A LA PAGINA 1 (ON HI HA EL FORMULARI DE CERCA PER CONVOCATORIRA I PREGUNTA).
function vesEndarrere() {
    destrueixFormulari();    //elimina tots els fills del contenidor del formulari creats programmatically previament
    eliminaIMG_delLi_area(); //elimina la imatge de l'àrea creada com a filla de un li.
    amaga_segonaPagina();    //amaga el contenido de la segona pagina.
    elimina_text_previ_cercador(); //elimina el text vestigial del cercador de la pagina 1 previa
    mostra_Cercador();       //torna a mostrar el cercador de la pagina 1 previa
    
}


//PRE: - intAreaPsico (param): una de les arees de la psico (enter)
//     - dArees (v global, dArees.js): existeix el diccionari d'arees
//       com a variable global per als hovers(ara esta dins dArees.js)
//     - dArees_src (v global, dArees.js): existeix el diccionari 
//       d'arees com a variable global (ara esta dins dArees.js)
//POST: A. Es rellena a l'element li amb id "displayImgArea" la etiqueta d'imatge. També es posen programmatically els 
//      atributs src dels valors de dArees_src, alt text i title.
//
//   <img src="psicometria.png" alt="NO S'HA TROBAT" title="lareaqueSigui">
function posaImgArea(intAreaPsico) {
    var node_li_ImatgeArea = document.getElementById("displayImgArea");
    var nodeIMGarea = document.createElement("img");

    nodeIMGarea.setAttribute("alt","no se ha podido cargar!")
    nodeIMGarea.setAttribute("src","./img/xhdpi/"+dArees_src[intAreaPsico]+".png") //afegeixo atribut "src = "/img/hdpi"" dins el node imatge
    nodeIMGarea.setAttribute("title",dArees[intAreaPsico]);

    node_li_ImatgeArea.appendChild(nodeIMGarea); //ageeixo el node imatge a dins l'element li que contindra la imatge
}
    



/*CREARÀ AIXÒ (AMB 4 O 5 OPCIONS DE RESPOSTA SEGONS LI PASSEM PER PAREAMETRE)
    <form>
        <p>Si us plau, seleccioneu la vostra opció preferida:</p>
        <input type="radio" id="option1" name="grupOpcioPregunta" value="option1">
        <label for="option1">Opció 1</label><br>
        <input type="radio" id="option2" name="grupOpcioPregunta" value="option2">
        <label for="option2">Opció 2</label><br>
        <input type="radio" id="option3" name="grupOpcioPregunta" value="option3">
        <label for="option3">Opció 3</label><br>
        <input type="radio" id="option4" name="grupOpcioPregunta" value="option4">
        <label for="option4">Opció 4</label><br>
        <button type="button" onclick="comprovaResposta()">Check Answer</button>
        <button type="button" onclick="vesEndarrere()">Go Back</button>
    </form>
*/

//PRE: - nreOpcionsResp: 4 o 5 (que dependra de l'examen)
//     - convocatoria: l'any de convocatoria que entra pel formulari (un any de convocatoria
//     - pregunta: pregunta de l'examen que entra pel formulari
//)   
//POST: Es mostra l'estructura de la segona pagina mostrant item amb id (contenidorItem2APAGINA) 
//      I es creen els fills dins del contenidor del formulari
async function mostra_estructura_i_crea_pregunta(nreOpcionsResp, convocatoria, pregunta) {
   
    document.getElementById("contenidorItem2APAGINA").style.display="block";

    //LA SEGUENT LINIA FORÇA A LA PREGUNT AA PILLAR EL DICCIONARI JSON DE ITEMS DES DEL SERVIDOR
    //localStorage.clear(); // Clearing the localStorage for testing purposes SI AIXO ESTA ACTIVAT ES COM SI NO HI HAGUES RES AL LOCAL STORAGE I FARA FETCH DEL SERVER.
    //console.log("local storage s'ha netejat amb localStrorage.clear()")
    //var arrayItem = obtinguesItem_remot_o_localStorage("2020","44"); //ojo amb els UTFS I MERDES que es error de python
  
    
    //OBTINC LA PREGUNTA SIGUI DEL SERVIDOR O DEL LOCAL STORAGE (fem servir await perquè hi ha una crida a funcio async)
    try {       
        var arrItem = await obtinguesItem_remot_o_localStorage(convocatoria, pregunta); //CARREGA LES VARIABLES DINS 
        var [strEnunciat, op1, op2, op3, op4, intAreaPsico] = arrItem;    
    } catch (error) {
        console.error(error);
    }

    //AFEGEIXO --> <img src="psicometria.png" alt="NO S'HA TROBAT"> a "displayImgArea" ()
    document.getElementById("displayConvocatoria").innerText = "PIR "+convocatoria;
    posaImgArea(intAreaPsico);  
    
    
    var array_contingutTextOpcioResposta = [op1, op2, op3, op4]; //les 
    //CREO ELS NODES QUE NECESSITO PER L'ESTRUCTURA DEL FORMULARI
    var contenidorPregunta = document.getElementsByClassName("contenidorFormPregunta")[0];
    var elementFormulari = document.createElement("form");
    var element_p_enunciat = document.createElement("p"); //creo un element html
   
    //POSO ELS NODES ANIDANT L'UN DINS L'ALTRE EN ESTRUCTURA ARBORESCENT
    //(NOTA QUE AFEGEIXO PRIMER ELS PARES I DESPRES ELS FILLS, PERO SERIA PERFECTAMENT VALID
    //I FUNCIONAL AFEGIR PRIMER ELS FILLS QUE ELS PARES (EN ORDRE EXACTAMENT INVERS, POSAR LES 3 SEGUENTS LINIES)
    contenidorPregunta.appendChild(elementFormulari); //AFEGEIXO <form></form> a contenidor.
    elementFormulari.appendChild(element_p_enunciat); //AFEGEIXO 
    element_p_enunciat.appendChild(document.createTextNode(strEnunciat));       

    for (let i = 0; i < nreOpcionsResp; ++i) {
        // Create input element
        var inputElement = document.createElement("input");
        inputElement.setAttribute("type", "radio");
        inputElement.setAttribute("id", "opcio"+(i+1)); //opcio1, opcio2, opcio3, opcio4, opcio5 segons item
        inputElement.setAttribute("name", "grupOpcioPregunta"); //IGUAL PER TOTS: amb el que pillaras els valors del que esta clicat i del que no
        inputElement.setAttribute("value", ""+(i+1)); //op1, op2, op3, op4, op5 segons item (es amb el que)

        // Create label element
        var labelElement = document.createElement("label");
        labelElement.setAttribute("for", "opcio"+(i+1)); //ha de ser correlatiu a atribut id perque pugui clicar l'usuari sobre el text
        labelElement.textContent = array_contingutTextOpcioResposta[i];
        labelElement.setAttribute("id", "etiquetaForm"+(i+1));

        // Create line break element
        var brElement = document.createElement("br");

        // Append the input, label, and line break elements to the container
        elementFormulari.appendChild(inputElement);
        elementFormulari.appendChild(labelElement);
        elementFormulari.appendChild(brElement);
    }

    //CREO BOTONS PER ANAR ENDARRERE I COMPROVAR LA RESPOSTA
    var divEmbolcallaBotons = document.createElement("div");
    divEmbolcallaBotons.setAttribute("id","embolcallBotonsFormPregunta");
    var checkButton = document.createElement("button");
    checkButton.setAttribute("type", "button");
    checkButton.setAttribute("id","boto_comprova")
    checkButton.textContent = "Comprueba";

    //PASSO EL PARAMETRE A LA FUNCIÓ QUE COMPROVE RESPOSTA
    checkButton.addEventListener("click", comprovaResposta); // L'EVENT LISTEWNER PILLA LA respCorrGS com a variable global OJO

    /* NO FUNCIONA NI PATRÁS... HE HAGUT DE FER LA VARIABLE respCorrGS una variable global.
    checkButton.addEventListener("click", function() {
        comprovaResposta(respCorrGS);
    });
    */

    var backButton = document.createElement("button");
    backButton.setAttribute("type", "button");
    backButton.setAttribute("id","btn_retorn_primeraPg")
    backButton.textContent = "Busca otro ítem!";
    backButton.addEventListener("click", vesEndarrere); // Add event listener to the button

    // Append buttons to the form
    elementFormulari.appendChild(divEmbolcallaBotons);
    divEmbolcallaBotons.appendChild(checkButton);
    divEmbolcallaBotons.appendChild(backButton);
}

//CONTINUA PER AQUI PER FER LA LECTURA ASINCRONA DE metadata i fes que torni true nomes quan una pregunta
//esta en l'interval de buscar-la (AQUESTA FUNCIO DONA ERROR OJO PERQUE HI HA LAGUN PROBLEMA )
async function avaluaDadesEntrants_1aPagina(convocatoria) {
    try {       
        var arrMetadata = await obtinguesMetadataExamen_remot_o_localStorage(convocatoria); //CARREGA LES VARIABLES DINS    
    } catch (error) {
        console.error(error);
    }
    return arrMetadata;
}

async function botoClicat() {
    var convocatoria = document.getElementsByName("convocatoria")[0].value; //PILLES L'ATRIBUT value (que entra pel formulari) de l'element input amb name = "convocatoria"
    var pregunta = document.getElementsByName("pregunta")[0].value;
    
    
    //COMPROVEM QUE CAP DELS DOS CAMPS ESTIGUIN BUITS PRIMER
    if (!convocatoria || !pregunta)
        alert("Rellena la convocatoria y la pregunta");
    else {
        //COMPROVEM QUE S'HAGI INTRODUIT UN NOMBRE
        if (!esNombre(convocatoria)) {
            alert("Introduce un año en la convocatoria (formato aaaa)!");
        } 
        else if (!esNombre(pregunta)) {
            alert("Introduce un número de item!");
        } 
        else {  
            //PASSEM PER PARSEINT PER ASSEGURARNOS QUE NO H IHA ZERO PADDING (0003 en preguntaes convertira a 3, per exemple)
            //I RETORNEM A STIRNG QUE ES EL QUE ESTEM TOCANT OTTA L'ESTONA
            convocatoria = parseInt(convocatoria, 10).toString();
            pregunta = parseInt(pregunta, 10).toString(); 
            //COMPROVO QUE LA CONVOCATORIA AFEGIDA ESTIGUI DINS EL RANG ADEQUAT
            if (convocatoria !=  minConvoc) {
                alert(`Cuidado! exámenes solo para la convocatoria de ${minConvoc}`);
            } else if (pregunta < 1 || pregunta > 3) {
                alert(`¡Esta página es una muestra! Solo encontrarás los items 1, 2 y 3 de la convocatoria 2020.`)
            }
            else {
                //EXTREC DADES DE LA METADATA DEL LOCAL STORAGE O DE LA BBDD JSON!
                try {
                    var arrMetadata = await avaluaDadesEntrants_1aPagina(convocatoria);
                    var [nPreguntes, nPreguntesReserva, duradaMinuts, NopcionsDeResposta] = arrMetadata; 

                    //COMPROVO QUE LA PREGUNTA ESTA EN EL RANG DE L'EXAMEN
                    if (pregunta <= (nPreguntes + nPreguntesReserva) && pregunta > 0) { //ITEMS EN EL RANG ADECUAT PER L'EXAMEN EN CUESTIO
                        amaga_Cercador();
                        mostra_estructura_i_crea_pregunta(NopcionsDeResposta, convocatoria, pregunta);  //MILLORAR ELS CATCH DE DINS LA FUNCIO
                        console.log(convocatoria,pregunta);
                    } else {
                        alert(`Examen del año ${convocatoria} tiene items comprendidos entre el 1 y el ${nPreguntes + nPreguntesReserva}!`);
                    }


                } catch (error) {
                    console.error(error);
                }










            }







        }


















    }

    


}
//ESBORRA LES PROPERES 3 LINIES QUAN ESTIGUIS SATISFET AMB LA SEGONA PAGINA (aIXO VA DIRECTE A LA SEGONA PAGINA)
//INICI SECCIO QUE CAL ESBORRAR

//amaga_Cercador();      
//var opcionsDeResposta = 4; //OBTINGUES DE METADATA DEL FRONTEND QUAN LA TINGUIS
//mostra_estructura_i_crea_pregunta(opcionsDeResposta);                


//FI SECCIO QUE CAL ESBORRAR
