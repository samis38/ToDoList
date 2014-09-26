package controller;

import model.Event;
import model.EventList;

//make other class clear, and easy to use
public class EventListController {
	
	private static EventList eventList = null;
	
	static public EventList getEventList(){
		if (eventList == null){
			eventList = new EventList();
		}
		return eventList;
	}
	
	public void addEvent(Event newEvent){
		getEventList().addEvent(newEvent);
	}
}
