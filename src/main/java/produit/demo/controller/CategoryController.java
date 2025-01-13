package produit.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import produit.demo.model.Category;
import produit.demo.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;  // Service pour gérer la logique métier des catégories

    // Afficher la liste des catégories
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category-list"; // Vue pour afficher toutes les catégories
    }

    // Afficher le formulaire d'ajout de catégorie
    @GetMapping("/new")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "create-category"; // Vue pour le formulaire d'ajout de catégorie
    }

    // Ajouter une catégorie après soumission du formulaire
    @PostMapping("/new")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category); // Sauvegarder la catégorie via le service
        return "redirect:/categories"; // Rediriger vers la liste des catégories
    }

    // Afficher le formulaire pour modifier une catégorie
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        model.addAttribute("category", category);
        return "edit-category"; // Vue pour le formulaire de modification
    }

    // Modifier une catégorie (mise à jour)
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        categoryService.update(id, category); // Utiliser le service pour mettre à jour la catégorie
        return "redirect:/categories"; // Rediriger vers la liste des catégories après mise à jour
    }

    // Supprimer une catégorie
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id); // Supprimer la catégorie via le service
        return "redirect:/categories"; // Rediriger vers la liste des catégories après suppression
    }
    @GetMapping("/{id}/products")
    public String listProductsByCategory(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        model.addAttribute("category", category);
        model.addAttribute("products", category.getProducts());
        return "products-by-category"; // Vue pour afficher les produits d'une catégorie
    }

}
