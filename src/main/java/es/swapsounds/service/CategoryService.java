package es.swapsounds.service;

import es.swapsounds.model.Category;
import es.swapsounds.storage.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void addCategoryIfNotExists(String name) {
        String normalizedName = name.trim();
        if (!categoryRepository.existsByNameIgnoreCase(normalizedName)) {
            Category category = new Category(normalizedName);
            categoryRepository.save(category);
        }
    }

    @Transactional(readOnly = true)
    public Category findOrCreateCategory(String name) {
        return categoryRepository.findByNameIgnoreCase(name.trim())
            .orElseGet(() -> {
                Category newCategory = new Category(name.trim());
                return categoryRepository.save(newCategory);
            });
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
} 
