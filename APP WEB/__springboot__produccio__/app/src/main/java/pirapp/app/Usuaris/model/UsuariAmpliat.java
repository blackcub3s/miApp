//CAL TESTEJAR CLASSE

package pirapp.app.Usuaris.model;


import jakarta.persistence.*;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuari_ampliat")
@Data   //afegeix els getters, setters i els metodes toString, equals i hashCode (que no se que fa aquest ultim).
@NoArgsConstructor  //afegeix el constructor sense arguments
@AllArgsConstructor //afegeix el constructor amb tots els arguments
public class UsuariAmpliat {

    @Id
    @Column(name = "id_usuari")
    private Integer idUsuari;

    @Column(name = "nom")
    private String nom;

    @Column(name = "primer_cognom")
    private String primerCognom;

    @Column(name = "segon_cognom")
    private String segonCognom;


}
