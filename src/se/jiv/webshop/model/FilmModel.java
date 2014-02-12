package se.jiv.webshop.model;

public class FilmModel extends ProductModel
{
	private final String title;
	private final int rating;
	private final String format;
	private final String director;
	
	public static class Builder extends ProductModel.Builder
	{
		// Required parameters
		private String title;
		private int rating;
		private String director;
		
		// Optional parameters
		private String format;
		
		public Builder(String name, String title, int rating, String director)
		{
			super(name);
			this.director = director;
			this.title = title;
			this.rating = rating;
			this.director = director;
			format = "";
		}
		
		public Builder format(String format)
		{
			this.format = format;
			return this;
		}
		
		@Override
		public FilmModel build()
		{
			return new FilmModel(this);
		}
	}
	
	protected FilmModel(Builder builder)
	{
		super(builder);
		this.title = builder.title;
		this.format = builder.format;
		this.director = builder.director;
		this.rating = builder.rating;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getFormat()
	{
		return format;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public int getRating()
	{
		return rating;
	}
	
	@Override
	public String toString()
	{
		return super.toString()
				+ String.format(" - Film: %s by %s - %s in format %s ", this.getTitle(),
						this.getFormat(), this.getDirector(), this.getFormat());
	}
	
	@Override
	public int hashCode()
	{
		int result = 1;
		result += 37 * this.getTitle().hashCode();
		result += 37 * this.getDirector().hashCode();
		
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}
		
		if (other instanceof FilmModel)
		{
			FilmModel otherBook = (FilmModel) other;
			boolean isSameClass = this.getClass().equals(otherBook.getClass());
			
			return (this.getTitle().equals(otherBook.getTitle()))
					&& this.getDirector().equals(otherBook.getDirector()) && isSameClass;
		}
		
		return false;
	}
}
