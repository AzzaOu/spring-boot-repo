package produit.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import produit.demo.model.Category;
import produit.demo.model.Product;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private produit.demo.repository.CategoryRepository categoryRepository;

    /**
     * Récupère toutes les catégories.
     * @return Liste de catégories.
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Trouve une catégorie par son ID.
     * @param id ID de la catégorie.
     * @return La catégorie correspondante ou null si elle n'existe pas.
     */
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    /**
     * Sauvegarde une nouvelle catégorie.
     * @param category L'objet Category à sauvegarder.
     */
    public void save(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Met à jour une catégorie existante.
     * @param id ID de la catégorie à mettre à jour.
     * @param updatedCategory Les données mises à jour.
     */
    public void update(Long id, Category updatedCategory) {
        Category existingCategory = findById(id);
        if (existingCategory == null) {
            throw new RuntimeException("Category with ID " + id + " not found");
        }
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());
        categoryRepository.save(existingCategory);
    }

    /**
     * Supprime une catégorie par son ID.
     * @param id ID de la catégorie à supprimer.
     */
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        Category category = findById(categoryId);
        if (category != null) {
            return category.getProducts();
        }
        return null;
    }

}
