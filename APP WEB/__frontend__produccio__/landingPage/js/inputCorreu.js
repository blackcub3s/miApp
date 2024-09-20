//PER FER QUE EL BOUNDING BOX DE L'INPUT DEL CORREU ES POSI DE COLOR BLAU
//QUAN EL CLIQUES

//PRE: Existeix un element inputs a la pàgina amb id email i una etiqueta amb id "email-labeleta"
//POST: Element inputs amb id "email" queda pintat en color blau i també l'etiqueta d'id "email-labeleta"
document.addEventListener('DOMContentLoaded', function() {
    
    // Carrego l'element que conté l'email (l'input)
    const emailInput = document.getElementById('email');
    const emailEtiqueta = document.getElementById("email-labeleta");

    // FUNCIÓ QUE POSA EL CONTORN DE L'ELEMENT EN BLAU IGUAL QUE A LA WEB DE GOOGLE
    function posaContornEnBlau() {
        emailInput.classList.add('input-border-blau');
        emailInput.classList.remove('input-border-default');           
    }

    function pintaColorEtiquetaEnBlau() {
        emailEtiqueta.style.color = "var(--contorn_blau)";
    }

    function treuColorEtiquetaEnBlau() {
        emailEtiqueta.style.color = "var(--foscMeu)";
    }

    // FUNCIÓ QUE TREU EL CONTORN DE L'ELEMENT EN BLAU POSANT EL COLOR PER DEFECTE
    function treuContornBlau() {
        emailInput.classList.add('input-border-default');
        emailInput.classList.remove('input-border-blau');
    }

    // SI L'INPUT OBTÉ EL FOCUS, POSO EL CONTORN I EL COLOR BLAU
    emailInput.addEventListener('focus', function() {
        posaContornEnBlau();
        pintaColorEtiquetaEnBlau();
    });

    // SI L'INPUT PERD EL FOCUS, TREU EL COLOR I EL CONTORN BLAU
    emailInput.addEventListener('blur', function() {
        treuContornBlau();
        treuColorEtiquetaEnBlau();
    });
});
