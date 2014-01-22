package se.jiv.webshop.model;

public final class CategoryModel {
	public static final int DEFAULT_ID = -1;
	
	private Integer id;
	private String name;
	private Integer staff_responsible;

	public CategoryModel(String name, Integer staff_responsible) {
		this.id = DEFAULT_ID;
		this.name = name;
		this.staff_responsible = staff_responsible;
	}

	public CategoryModel(Integer id, CategoryModel other) {
		this.id = id;
		this.name = other.name;
		this.staff_responsible = other.staff_responsible;
	}

	public CategoryModel(CategoryModel other) {
		this(other.id, other);
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getStaff_responsible() {
		return staff_responsible;
	}
	
	public String toString() {
		return String.format("Id: %s, Name: %s, Straff_responsible: %s", id,
				name, staff_responsible);
	}

}