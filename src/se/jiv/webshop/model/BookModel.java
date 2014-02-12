package se.jiv.webshop.model;

public class BookModel extends ProductModel
{
	private final String title;
	private final int isbn;
	private final int pages;
	private final String publisher;
	private final String format;
	private final int author;
	
	public static class Builder extends ProductModel.Builder
	{
		// Required parameters
		private String title;
		private int isbn;
		private int pages;
		private String publisher;
		private int author;
		
		// Optional parameters
		private String format;
		
		public Builder(String name, int author, String title, int isbn, int pages, String publisher)
		{
			super(name);
			this.title = title;
			this.author = author;
			this.isbn = isbn;
			this.pages = pages;
			this.publisher = publisher;
			format = "";
		}
		
		public Builder format(String format)
		{
			this.format = format;
			return this;
		}
		
		@Override
		public BookModel build()
		{
			return new BookModel(this);
		}
	}
	
	protected BookModel(Builder builder)
	{
		super(builder);
		this.title = builder.title;
		this.isbn = builder.isbn;
		this.pages = builder.pages;
		this.publisher = builder.publisher;
		this.format = builder.format;
		this.author = builder.author;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getIsbn()
	{
		return isbn;
	}
	
	public int getPages()
	{
		return pages;
	}
	
	public String getPublisher()
	{
		return publisher;
	}
	
	public String getFormat()
	{
		return format;
	}
	
	public int getAuthor()
	{
		return author;
	}
	
	@Override
	public String toString()
	{
		return super.toString()
				+ String.format(" - Book: %s by %s | %s | published by %s - format: %s, pages %s",
						this.title, this.author, this.isbn, this.publisher, this.format, this.pages);
	}
	
	@Override
	public int hashCode()
	{
		int result = 1;
		result += 37 * this.getIsbn();
		
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}
		
		if (other instanceof BookModel)
		{
			BookModel otherBook = (BookModel) other;
			boolean isSameClass = this.getClass().equals(otherBook.getClass());
			
			return (this.isbn == otherBook.isbn) && isSameClass;
		}
		
		return false;
	}
}