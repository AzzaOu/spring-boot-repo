package produit.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import produit.demo.model.Product;
import produit.demo.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Récupérer tous les produits
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    // Trouver un produit par son ID
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);  // Retourner null si le produit n'existe pas
    }

    // Sauvegarder un produit
    public void save(Product product) {
        productRepository.save(product);
    }

    // Mettre à jour un produit
    public void update(Long id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setMarque(product.getMarque());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setDescription(product.getDescription());
            productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    // Supprimer un produit
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public boolean deleteProductById(Long id) {
        if (productRepository.existsById(id)) {  // Vérifie si le produit existe
            productRepository.deleteById(id);  // Supprime le produit
            return true;  // Retourne vrai si la suppression a réussi
        }
        return false;  // Retourne faux si le produit n'existe pas
    }
    // Méthode pour récupérer tous les produits
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}
