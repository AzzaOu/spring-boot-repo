package produit.demo.exception;

public class ProductNotFoundException extends RuntimeException {
    // Constructeur pour accepter l'ID du produit
    public ProductNotFoundException(Long id) {
        super("Product not found with id " + id);
    }
}
