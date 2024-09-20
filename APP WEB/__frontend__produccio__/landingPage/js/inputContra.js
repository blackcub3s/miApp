function contrasenyaApta(contra) {
    return contra.length > 5;
}

//VOLEM LA BOUNDING BOX I L'ETIQUETA DE LA CONTRASENYA CORRECTAMENT MOSTRADES (AQUEST FITXER GENERA ESTILS CSS PROGRAMMATICALLY)
document.addEventListener('DOMContentLoaded', function() {

    //Carrego l'element que conté l'email (l'input)
    const contrasenyaInput = document.getElementById('contrasenya');
    const contrasenyaEtiqueta = document.getElementById("contra-labeleta");

    //FUNCIÓ QUE POSA EL CONTORN DE L'ELEMENT EN BLAU IGUAL QUE A LA WEB DE GOOGLE
    function posaContornEnBlau() {
        contrasenyaInput.classList.add('input-border-blau');
        contrasenyaInput.classList.remove('input-border-default');           
    }

    function pintaColorEtiquetaEnBlau() {
        contrasenyaEtiqueta.style.color = "var(--contorn_blau)";
    }

    function treuColorEtiquetaEnBlau() {
        contrasenyaEtiqueta.style.color = "var(--foscMeu)";
    }

    //FUNCIÓ QUE TREU EL CONTORN DE L'ELEMENT EN BLAU POSANT EL COLOR PER DEFECTE
    function treuContornBlau() {
        contrasenyaInput.classList.add('input-border-default');
        contrasenyaInput.classList.remove('input-border-blau');
    }

    // SI L'INPUT OBTÉ EL FOCUS POSO EL CONTORN I EL COLOR BLAU
    contrasenyaInput.addEventListener('focus', function() {
        posaContornEnBlau();
        pintaColorEtiquetaEnBlau();
    });

    // SI L'INPUT PERD EL FOCUS, TREU EL COLOR I EL CONTORN BLAU
    contrasenyaInput.addEventListener('blur', function() {
        treuContornBlau();
        treuColorEtiquetaEnBlau();
    });
});
