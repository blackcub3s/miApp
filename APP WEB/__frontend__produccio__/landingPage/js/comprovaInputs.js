

//PER ACONSEGUIR QUE S'ENVII EL CORREU SOTA LES MATEIXES CONDICIONS
// QUE LES DE POSAR VERD EL BOUNDING BOX (SEGUENT VENETLISTENER). S'activara nomes en cliar el borto d'enviar el correu al registre
function correuApte(strEmail) {
    const atIndex = strEmail.indexOf('@');
    const dotIndex = strEmail.indexOf('.', atIndex);
    const noHiHaComes = strEmail.indexOf(",") == -1;

    const isValid = (
        atIndex > 0 &&                      
        dotIndex > atIndex + 1 &&           
        dotIndex < strEmail.length - 1 &&
        noHiHaComes
    );
    return isValid;
}