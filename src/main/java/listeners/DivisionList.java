package listeners;

import java.util.ArrayList;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sheets.PointsSheet;

public class DivisionList extends ListenerAdapter{
	
	private PointsSheet pointsSheet;
	
	public DivisionList(PointsSheet sheet) {
		this.pointsSheet = sheet;
	}
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		Message.suppressContentIntentWarning();
		if(event.getName().equalsIgnoreCase("division")) {
			
			event.deferReply().queue();
			
			String division = event.getOption("division").getAsString();
			
			if(division != null) {
				if(event.getUser().getName().equals("alekjay")) {
					ArrayList<String> divisionList = pointsSheet.getDivisionNames(division);
					String printString = "Division " + division + ":";
					for(String s : divisionList) printString += "\n" + s;
					event.getHook().sendMessage(printString).queue();
				}else {
					event.getHook().sendMessage("Permission Denied").queue();
				}
				return;
			}
			event.getHook().sendMessage("Something went wrong!").queue();
			
		}
		
	}

}
