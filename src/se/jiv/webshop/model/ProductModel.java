package se.jiv.webshop.model;

import java.util.ArrayList;
import java.util.List;

public final class ProductModel {
	public static final int DEFAULT_PRODUCT_ID = -1;

	int id;
	String name;
	String description;
	double cost;
	double rrp;
	List<Integer> categories;

	public ProductModel(int id, String name, String description, double cost,
			double rrp, List<Integer> categories) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.rrp = rrp;
		this.categories = new ArrayList<Integer>();
		if (categories != null) {
			this.categories.addAll(categories);
		}
	}

	public ProductModel(String name, String description, double cost,
			double rrp, List<Integer> categories) {
		this(DEFAULT_PRODUCT_ID, name, description, cost, rrp, categories);
	}

	public ProductModel(int id, String name, String description, double cost,
			double rrp) {
		this(name, description, cost, rrp, null);
	}

	public ProductModel(String name, String description, double cost) {
		this(name, description, cost, 0, null);
	}

	public ProductModel(String name, String description, double cost,
			List<Integer> categories) {
		this(name, description, cost, 0, categories);
	}

	public ProductModel(String name, String description) {
		this(name, description, 0, 0, null);
	}

	public ProductModel(String name, String description,
			List<Integer> categories) {
		this(name, description, 0, 0, categories);
	}

	public ProductModel(String name, double cost) {
		this(name, "", cost, 0, null);
	}

	public ProductModel(String name, double cost, List<Integer> categories) {
		this(name, "", cost, 0, categories);
	}

	public ProductModel(String name) {
		this(name, "", 0, 0, null);
	}

	public ProductModel(String name, List<Integer> categories) {
		this(name, "", 0, 0, categories);
	}

	public ProductModel(int id, ProductModel other) {
		this(id, other.name, other.description, other.cost, other.rrp,
				other.categories);
	}

	public ProductModel(ProductModel other) {
		this(other.id, other);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public double getCost() {
		return this.cost;
	}

	public double getRrp() {
		return this.rrp;
	}

	public List<Integer> getCategories() {
		return categories;
	}

	@Override
	public int hashCode() {
		return 37 * id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof ProductModel) {
			ProductModel other = (ProductModel) obj;
			return other.id == this.id;
		}
		return false;
	}

	@Override
	public String toString() {
		String categoriesTxt = "[";
		boolean first = true;
		for (int cat : this.categories) {
			if (!first) {
				categoriesTxt += ", ";
			} else {
				first = false;
			}
			categoriesTxt += cat;
		}
		categoriesTxt += "]";

		return String
				.format("Id: %s, Name: %s, Description: %s, Cost: %s, RRP: %s, Categories: %s",
						id, name, description, cost, rrp, categoriesTxt);
	}

}
