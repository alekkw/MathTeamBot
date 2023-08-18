package sheets;

import net.dv8tion.jda.api.entities.User;
import tools.Format;
import tools.Format.Category;

public class GoogleSheetPoints extends GoogleSheet{
	
	private Format format;
	
	public GoogleSheetPoints(String applicationName, String spreadsheetId, Format format) {
		super(applicationName, spreadsheetId);
		this.format = format;
	}
	
	/**
	 * Adds points to the specified user
	 * @param name: First and Last name of the user
	 * @param pointsAdded: The number of points to be added to the member
	 * @return
	 */
	public boolean addPoints(String name, int pointsAdded) {
		int nameIndex = format.getCategoryLocation(Category.NAME);
		if(nameIndex != -1) {
			int columnSize = getColumnLength(nameIndex);
			for(int i = 0; i < columnSize; i++) {
				if(get(nameIndex, i).equalsIgnoreCase(name)) {
					change(format.getCategoryLocation(Category.POINTS), i, Integer.parseInt(get(format.getCategoryLocation(Category.POINTS), i)) + pointsAdded);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the points a user has
	 * @param name: First and Last name of the user
	 * @return the number of points the specified user has
	 */
	public int getPoints(String name) {
		int nameIndex = format.getCategoryLocation(Category.NAME);
		if(nameIndex != -1) {
			int columnSize = getColumnLength(nameIndex);
			for(int i = 0; i < columnSize; i++) {
				if(get(nameIndex, i).equalsIgnoreCase(name)) {
					return Integer.parseInt(get(format.getCategoryLocation(Category.POINTS), i));
				}
			}
		}
		return -1;
	}
	
	/**
	 * Returns the points a user has
	 * @param user: Discord User of the user
	 * @return the number of points the specified user has
	 */
	public int getPoints(User user) {
		int discordIdIndex = format.getCategoryLocation(Category.DISCORD_ID);
		if(discordIdIndex != -1) {
			int columnSize = getColumnLength(discordIdIndex);
			for(int i = 0; i < columnSize; i++) {
				if(get(discordIdIndex, i).equals(user.toString())) {
					return Integer.parseInt(get(format.getCategoryLocation(Category.POINTS), i));
				}
			}
		}
		return -1;
	}
	
	public String getName(User user) {
		int discordIdIndex = format.getCategoryLocation(Category.DISCORD_ID);
		if(discordIdIndex != -1) {
			int columnSize = getColumnLength(discordIdIndex);
			for(int i = 0; i < columnSize; i++) {
				if(get(discordIdIndex, i).equals(user.toString())) {
					return String.valueOf(get(format.getCategoryLocation(Category.NAME), i));
				}
			}
		}
		return null;
	}
	
	public boolean addMemberToDatabase(Object... parameters) {
		int newRow = getColumnLength(0);
		for(int i = 0; i < parameters.length; i++) {
			change(i,newRow,parameters[i]);
		}
		return true;
	}

}
