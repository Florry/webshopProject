package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.model.CategoryModel;

public interface CategoryRepository {

	public CategoryModel addCategory(CategoryModel category);

	public CategoryModel getCategory(Integer id);

	public List<CategoryModel> getAllCategories();

	public boolean deleteCategory(Integer id);

}
