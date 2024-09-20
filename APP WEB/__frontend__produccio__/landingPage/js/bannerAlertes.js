
function bannerAlerta(parametresAlerta, tipusAlerta, colorAlerta) {
    const banner = document.getElementById("bannerMissatges");  
    banner.style.marginTop = "1em";
    banner.style.fontSize = ".7em";

    if (tipusAlerta == "usuariInexistent") {
        banner.innerHTML = `<p>¡El usuario <strong>${parametresAlerta[0]}</strong> no está registrado! <span id="closeBanner" style="cursor:pointer; font-weight:bold; margin-left:10px;">&times;</span></p>`;
    } else if (tipusAlerta == "usuariExisteixPeroNoteAccesArecursos") {
        banner.innerHTML = `<p>¡Tu cuenta asociada a <strong>${parametresAlerta[0]}</strong> no tiene acceso a los recursos de la aplicación ahora mismo! ¡Contrata tu plan aqui! <span id="closeBanner" style="cursor:pointer; font-weight:bold; margin-left:10px;">&times;</span></p>`;
    } else if (tipusAlerta == "contrasenyaIncorrecta") {
        banner.innerHTML = `<p>¡La contraseña para la cuenta <strong>${parametresAlerta[0]}</strong> Es incorrecta. <span id="closeBanner" style="cursor:pointer; font-weight:bold; margin-left:10px;">&times;</span></p>`;
    } else if (tipusAlerta == "bienvenidoAlaApp") {
        banner.innerHTML = `<p> ¡Bienvenido a PirApp! <span id="closeBanner" style="cursor:pointer; font-weight:bold; margin-left:10px;">&times;</span></p>`;       
    } else if (tipusAlerta == "usuariExisteixError") {
        banner.innerHTML = `<p> !El usuario <strong>${parametresAlerta[0]} ya existe y no puede ser registrado!</p>`
    } else if (tipusAlerta == "algoFueMalConRegistro") {
        banner.innerHTML = `<p> ¡Hubo un problema inesperado con el registro, probablemente problemas de conexión con la bbdd, vuélvelo a intentar!</p>`
    }


    banner.style.border = "1px solid black";
    banner.style.backgroundColor = colorAlerta;
    banner.style.borderRadius = ".2em";
    banner.style.marginBottom = ".3em";
    banner.style.padding = ".7em .3em .7em .3em";


    //ESBORRAR EL BANNER
    document.getElementById("closeBanner").onclick = function() {
        banner.removeChild(banner.children[0]);
        banner.style.cssText = "";  // Elimino tots els estils
    };
}

