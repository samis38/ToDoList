package model;

import java.util.ArrayList;
/*Gson can not take the content in an array list of adaptor,
 * hence i need this class to return a array list of Event for Gson.
 * */
public class DiskList {
	private ArrayList<Event> eventList=null;
	
	public DiskList(ArrayList<Event> eventList){
		this.eventList=eventList;
	}
	
	public ArrayList<Event> getList(){
		return this.eventList;
	}
}