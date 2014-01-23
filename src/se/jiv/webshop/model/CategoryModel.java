package se.jiv.webshop.model;

public final class CategoryModel {
	public static final int DEFAULT_ID = -1;

	private int id;
	private String name;
	private int staff_responsible;

	public CategoryModel(int id, String name, int staff_responsible) {
		this.id = id;
		this.name = name;
		this.staff_responsible = staff_responsible;
	}

	public CategoryModel(String name, int staff_responsible) {
		this(DEFAULT_ID, name, staff_responsible);
	}

	public CategoryModel(int id, CategoryModel other) {
		this.id = id;
		this.name = other.name;
		this.staff_responsible = other.staff_responsible;
	}

	public CategoryModel(CategoryModel other) {
		this(other.id, other);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getStaff_responsible() {
		return staff_responsible;
	}

	public String toString() {
		return String.format("Id: %s, Name: %s, Straff_responsible: %s", id,
				name, staff_responsible);
	}

}
