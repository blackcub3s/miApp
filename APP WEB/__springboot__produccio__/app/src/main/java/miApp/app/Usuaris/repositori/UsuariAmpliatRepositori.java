//CLASSE FET PER XAT GPT. CAL TESTEJARLA

package miApp.app.Usuaris.repositori;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import miApp.app.Usuaris.model.UsuariAmpliat;

import java.util.Optional;


@Repository
public interface UsuariAmpliatRepositori extends JpaRepository<UsuariAmpliat, Integer> {

    // Troba si un usuariAmpliat existeix a partir de la seva clau primària (id_usuari)
    Optional<UsuariAmpliat> findById(Integer idUsuari);

    // Optional custom query methods can be added here if needed
    // Example: Find UsuariAmpliat by any other field if necessary
    // Optional<UsuariAmpliat> findByNom(String nom);

    // Example of a method to check if a UsuariAmpliat exists by the parent's id_usuari
    boolean existsById(Integer idUsuari);
}
