//PRE:  - diccItems: -tipo const-  es eldiccItems -object- amb l'estructura d'items anidada convocatoria item i preguntes (json_global like))
//      - strConvoc: un string de convocatoria ("2020", "2021" ...)
//      - strItem: 
//POST:  les variables trEnunciat, op1, op2, op3, op4, intAreaPsico passaen al script per del body
//       per ser utilitzades directament 
//       com un array que després podrem treure com a variables locals (sense).
//       - OJO: respCorrGS (resposta correcta Gold standard) queda com a variable global perque sino no se com fer que sigui accessible
//       per la funció que es crida amb l'event listener comprovaResposta(), que s'activa en fer click al boto comprova del formulari de l'item.
function aux_obtingues_diccItems(diccItems, strConvoc, strItem) {
    console.log(typeof(diccItems));
    var strEnunciat = diccItems[strConvoc][strItem]["enunciat"];
    var op1 = diccItems[strConvoc][strItem]["op1"];
    var op2 = diccItems[strConvoc][strItem]["op2"];
    var op3 = diccItems[strConvoc][strItem]["op3"];
    var op4 = diccItems[strConvoc][strItem]["op4"];
    var intAreaPsico = diccItems[strConvoc][strItem]["assignatura"];
    respCorrGS = diccItems[strConvoc][strItem]["resp_corr"]; //LA FAIG VARIABLE GLOBAL PER PODER ACCEDIRHI EN CLICAR AL BOTÓ COMRPOVA DEL FORMULARI

    var arrItem = [strEnunciat, op1, op2, op3, op4, intAreaPsico];
    return arrItem;
}

//AQUESTA FUNCIO GENERA UNA CRIDA ASÍNCRONA AL SERVIDOR PER A TREURE TOT EL DICCIONARI D'ITEMS I GUARDARLO I CONSULTARLO
//, SI I NOMES SI, NO HI HA AQUEST DICCIONARI AL LOCAL STORAGE. CAS EN EL QUAL SIMPLEMENT HO AGAFARÀ DEL LOCAL STORAGE
async function obtinguesItem_remot_o_localStorage(strConvoc, strItem) {
    var storedDades = localStorage.getItem("diccItems");

    //PROVO AGAFAR DADES BASE DE DADES (TREIENT EL JSON DEL SERVIDOR) EN CAS QUE NO LES TROBI AL LOCAL STORAGE
    if (!storedDades) {
        console.log("Obtenint items del servidor (i guardant-los al local storage)!");
        try {
            const response = await fetch('JSON/JSON_GLOBAL_copia.json');
            const Dades = await response.json();
            localStorage.setItem("diccItems", JSON.stringify(Dades));
            return aux_obtingues_diccItems(Dades, strConvoc, strItem);
        } catch (error) {
            //SALTA QUAN ES DEMANA UNA OPCIÓ DE RESPOSTA FORA DE LES QUE HI HA AL JSON DEL SERVIDOR
            throw new Error('Error obtenint diccItems del servidor (problema amb el JSON, o petició fora de rang en el frontend no manejada):', error);
        }
    //EN CAS CONTRARI LES AGAFA DIRECTAMENT DEL LOCAL STORAGE (QUE SERA EL CAS MES HABITUAL)
    } else {
        console.log("diccItems obtingut de l'emmagatzematge local (no s'ha fet petició al servidor :)).");
        const parsedDades = JSON.parse(storedDades);
        return aux_obtingues_diccItems(parsedDades, strConvoc, strItem);
    }
}



//localStorage.clear(); // Clearing the localStorage for testing purposes




