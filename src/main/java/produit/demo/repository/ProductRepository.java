package produit.demo.repository;

import produit.demo.model.Product;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    // Rechercher des produits par nom (ignorer la casse)
    List<Product> findByNameContainingIgnoreCase(String name);

    // La méthode deleteById est déjà fournie par CrudRepository, pas besoin de la redéfinir
    // Vous pouvez l'utiliser directement dans votre service
}
