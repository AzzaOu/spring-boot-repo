package produit.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import produit.demo.model.Category;
import produit.demo.model.Product;
import produit.demo.service.CategoryService;
import produit.demo.service.ProductService;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    // Afficher la liste des produits
    @GetMapping
    public String listProducts(Model model, @RequestParam(value = "message", required = false) String message) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("keyword", "");
        if (message != null) {
            model.addAttribute("message", message); // Ajouter un message de succès si présent
        }
        return "product-list";
    }

    // Afficher la liste des produits pour ROLE_USER
    @GetMapping("/list")
    public String listProductsForUser(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("keyword", "");
        return "product-list-user"; // Retourne une page sans CRUD
    }

    // Afficher le formulaire d'ajout de produit
    @GetMapping("/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "create-product";
    }

    // Ajouter un produit après soumission du formulaire
    @PostMapping("/new")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.save(product); // Sauvegarder le produit via le service
        return "redirect:/products?message=Product+added+successfully"; // Rediriger avec message de succès
    }

    // Afficher le formulaire d'édition du produit
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id); // Récupérer le produit avec son ID
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        model.addAttribute("product", product); // Ajouter le produit à la vue
        model.addAttribute("categories", categoryService.findAll()); // Ajouter les catégories à la vue
        return "edit-product"; // Afficher le formulaire d'édition
    }

    // Mettre à jour un produit après soumission du formulaire
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") Product product) {
        // Trouver le produit existant
        Product existingProduct = productService.findById(id);

        if (existingProduct != null) {
            // Mettre à jour les champs du produit
            existingProduct.setName(product.getName());
            existingProduct.setMarque(product.getMarque());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());

            // Récupérer la catégorie mise à jour par son ID
            Category updatedCategory = categoryService.findById(product.getCategory().getId());
            existingProduct.setCategory(updatedCategory); // Mettre à jour la catégorie du produit

            // Sauvegarder le produit avec la nouvelle catégorie
            productService.save(existingProduct);
        }

        // Rediriger vers la liste des produits après la mise à jour avec un message de succès
        return "redirect:/products?message=Product+updated+successfully";
    }

    // Méthode pour supprimer un produit
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProductById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Produit supprimé avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit non trouvé");
        }

    }


    // Rechercher des produits
    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("products", productService.searchByName(keyword));
        model.addAttribute("keyword", keyword);  // Conserver la valeur de la recherche
        return "product-list";
    }
}
