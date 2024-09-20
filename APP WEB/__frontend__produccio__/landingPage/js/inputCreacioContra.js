//ARXIUS ESPECIFIC PER A LA PAGINA DE CREACIO DE CONTRASENYA PER AL REGISTRE. TO DO.

//PRE: Una contrasenya.
//POST: retorna un array de booleans on, de forma correlativa, queda informat si l
//      la contrasneya 5 o més caràcters, una majúscula, una minúscula, un número i un caràcter
//      especial com un punt, una coma, un 
function contrasenyaAptaRegistre(strContra) {
    const llargadaCincOmes = strContra.length >= 5;
    const teMajuscula = /[A-Z]/.test(str);
    const teMinuscula = /[a-z]/.test(str);   
    const teNumero =  /[0-9]/.test(str); 
    const teCaracterEspecial = /^[!@#$%^&*()\-_+={}\[\]:;"'<>,.?\/\\|]+$/;
    
    return [llargadaCincOmes, teMajuscula, teMinuscula, teNumero];
}