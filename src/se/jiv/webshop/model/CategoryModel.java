package se.jiv.webshop.model;

public final class CategoryModel {
	private Integer id;
	private String name;
	private Integer staff_responsible;
	
	public CategoryModel(String name, Integer staff_responsible) {
		this.name = name;
		this.staff_responsible = staff_responsible;
	}
	
	public CategoryModel(Integer id, CategoryModel other){
		this.id = id;
		this.name = other.name;
		this.staff_responsible = other.staff_responsible;
	}
	
	public CategoryModel(CategoryModel other){
		this(other.id,other);
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
		StringBuilder sb = new StringBuilder();
		sb.append("Id: ");
		sb.append(id);
		
		sb.append("Name: ");
		sb.append(name);
		
		sb.append("Staff_Responsible: ");
		sb.append(staff_responsible);
	
		return sb.toString();
	}
	
}
