//PRE: Dadetes diccMetadata (un diccionari json amb l'estructura convocatoria
//     i valor un altre diccionari a mb parells de metadata -veure JSON_metadata_global.json-
//POST: les variables passaen a l'script quan es clica el boto de buscar a la pagina inicial
//      i defineixen si fem la cerca o no (per exemple fora de rang o any que no hi es)
function aux_obtingues_diccMetadata(Dadetes, strConvoc) {
    var nPreguntes = Dadetes[strConvoc]["preguntes"];
    var nPreguntesReserva = Dadetes[strConvoc]["preguntesReserva"];
    var duradaMinuts = Dadetes[strConvoc]["durada"];
    var nResp = Dadetes[strConvoc]["nreResp"];

    var arrMetaData = [nPreguntes, nPreguntesReserva, duradaMinuts, nResp];
    return arrMetaData;
}


async function obtinguesMetadataExamen_remot_o_localStorage(strConvoc) {
    var storedDadetes = localStorage.getItem("diccMetadata");
    
    //PROVO AGAFAR Dadetes BASE DE Dadetes (TREIENT EL JSON DEL SERVIDOR) EN CAS QUE NO LES TROBI AL LOCAL STORAGE
    if (!storedDadetes) {
        console.log("Obtenint metadata examens del servidor (i guardant-la al local storage)!");
        try {
            
            const responseta = await fetch('JSON/JSON_metadata_global.json');
            
            const Dadetes = await responseta.json();
            localStorage.setItem("diccMetadata", JSON.stringify(Dadetes)); //poso les Dadetes al local storage
            return aux_obtingues_diccMetadata(Dadetes, strConvoc);
        } catch (error) {
            //SALTA QUAN ES DEMANA UNA OPCIÓ DE RESPOSTA FORA DE LES QUE HI HA AL JSON DEL SERVIDOR
            throw new Error('Error obtenint diccMetadata del servidor (problema amb el JSON, o petició fora de rang en el frontend no manejada):', error);
        }
    //EN CAS CONTRARI LES AGAFA DIRECTAMENT DEL LOCAL STORAGE (QUE SERA EL CAS MES HABITUAL)
    } else {
        console.log("diccMetadata obtingut de l'emmagatzematge local (no s'ha fet petició al servidor :)).");
        const parsedDadetes = JSON.parse(storedDadetes);
        return aux_obtingues_diccMetadata(parsedDadetes, strConvoc);
    }
}


// Example usage:
//localStorage.clear(); // Clearing the localStorage for testing purposes

