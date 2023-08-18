package tools;

public class Format {
	
	Category[] categories;
	
	public Format(Category... categories) {
		this.categories = categories;
	}
	
	public int getCategoryLocation(Category categoryName) {
		for(int i = 0; i < categories.length; i++) {
			if(categories[i].equals(categoryName)) return i;
		}
		return -1;
	}
	
	public enum Category{
		NAME,
		POINTS,
		DISCORD_ID,
		DIVISION
	}

}
