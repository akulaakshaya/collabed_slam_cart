package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.Category;

public interface CategoryDAO {

	public List<String> getAllCategories();

	boolean addNewCategory(Category catg);
}