package se.jiv.webshop.service;

import java.util.List;

import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.CategoryRepository;

public final class CategoryService {
	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public CategoryModel addCategory(CategoryModel category) {
		return categoryRepository.addCategory(category);
	}

	public CategoryModel getCategory(Integer id) {
		return categoryRepository.getCategory(id);
	}

	public List<CategoryModel> getAllCategories() {
		return categoryRepository.getAllCategories();
	}

	public boolean deleteCategory(Integer id) {
		return categoryRepository.deleteCategory(id);
	}
}
