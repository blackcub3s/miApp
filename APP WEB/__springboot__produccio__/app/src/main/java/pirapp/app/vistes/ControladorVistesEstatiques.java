/*
package pirapp.app.controller;

//PER PODER FER ELS IMPORTS DE @Comtroller, @GetMapping i @RequestMapping fa falta tenir la dependència
//SPRING WEB INSTAL·LADA.Anàlogament, cal tenir spring-boot-starter-thymeleaf instalat també al pom.xml
//per poder tenir accés a les templates fent servir @Controller
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;



//@RequestMapping    NO CAL posar-lo si no volem definir una subcarpeta
@Controller  //poses controller perquè vols xuclar vistes html. Ojo! si poses @restcontroller es per a jsons.
public class ControladorVistesEstatiques {

    // ----  FEM EL MAPPING DE SOLICITUD HTTP GET DE L'APLICACIO      ONE-SHOT  ----
    @GetMapping("/one-shot")
    public ModelAndView appPreguntaAillada() {
        return new ModelAndView("forward:/one-shot/main__one-shot.html");   //retorna main__one-shot.html de dins carpeeta one-shot
    }

    // ----  FEM EL MAPPING DE SOLICITUD HTTP GET DE L'APLICACIO      EXAMENS ----
    @GetMapping("/examenes")  //url: localhost8080/examenes  -- mapeja a  -->   examens/examens.html
    public ModelAndView examensTipics() {
        return new ModelAndView("forward:/examens/examens.html");           //(retorna examen.html de dins la carpeta examens)
    }

    // ----  FEM EL MAPPING DE DE SOLICITUD HTTP GET DE L'APLICACIO    graf-IA ----
    @GetMapping("/grafo-IA")
    public ModelAndView aplicacioGrafIA() {
       return new ModelAndView("forward:/graf-IA/graf-IA.html");          //retorna graf-IA.html de dins carpeta
    }
}

*/
