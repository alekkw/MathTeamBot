package listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sheets.GoogleSheetPoints;

public class PrivatePoints extends ListenerAdapter{
	
	private GoogleSheetPoints sheet;
	private String[] authorizedUsers;
	
	public PrivatePoints(GoogleSheetPoints pointsSheet, String[] authorizedUsers) {
		this.sheet = pointsSheet;
		this.authorizedUsers = authorizedUsers;
	}
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		
		boolean validation = validate(event.getUser());
		
		if(validation) {
			
			String eventNature = event.getName();
		
			//Add user to database
			if(eventNature.equals("addmember")) {
				event.deferReply().queue();
				String name = event.getOption("membername").getAsString();
				User user = event.getOption("id").getAsUser();
				String division = 	event.getOption("division").getAsString();
				int points = 0;
				boolean success = sheet.addMemberToDatabase(name, user.toString(), division, points);
				if(success) {
					event.getHook().sendMessage("Successfully added new member").queue();
				}else {
					event.getHook().sendMessage("Failure in adding member").queue();
				}
			}
			
			//Add point to user
			if(eventNature.equals("addpoints")) {
				event.deferReply().queue();
				String name = event.getOption("membername").getAsString();
				int addedPoints = event.getOption("points").getAsInt();
				boolean success = sheet.addPoints(name, addedPoints);
				if(success) {
					event.getHook().sendMessage("Points were added successfully: Name: " + name + " Points: " + addedPoints).queue();
				}else {
					event.getHook().sendMessage("Points were NOT added: Name: " + name + " Points: " + addedPoints).queue();
				}
			}
			
			
		}
		
	}
	
	public boolean validate(User user) {
		for(String u : authorizedUsers) {
			if(u.equals(user.getName())) return true;
		}
		return false;
	}

}
