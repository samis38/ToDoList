package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.widget.ArrayAdapter;

public class EventList {
	
	private ArrayList<Event> eventList=null;
	private ArrayAdapter<Event> adapter=null;
	
	public EventList(){
		eventList = new ArrayList<Event>();
	}
	
	public EventList(ArrayList<Event> eventList){
		this.eventList=eventList;
	}
	
	public void setAdpater(ArrayAdapter<Event> adapter){
		this.adapter=adapter;
	}
	
	public void addEvent(Event event){
		eventList.add(event);
		if(this.adapter!=null){
			adapter.notifyDataSetChanged();
		}
	}
	
	public void removeEvent(){
		Iterator<Event> i=this.eventList.iterator();
		while (i.hasNext()){
			Event e=i.next();
			if(e.getStatus()){
				i.remove();
			}
		}
		if(this.adapter!=null){
			adapter.notifyDataSetChanged();
		}
	}
	
	public List<Event> getCurrentList(){
		return this.eventList;
	}
	
	public ArrayList<Event> getCurrentArrayList(){
		return this.eventList;
	}
	
	public void clickAnEvent(Event event){
		Iterator<Event> i=this.eventList.iterator();
		while (i.hasNext()){
			Event e=i.next();
			if(e.getId().equals(event.getId())){
				i.remove();
				break;
			}
		}
		if(event.getStatus()){
			event.unSelect();
		}
		else{
			event.select();
		}
		this.eventList.add(event);
		
		if(this.adapter!=null){
			adapter.notifyDataSetChanged();
		}
	}
	
	public ArrayList<Event> getTransferItems(){
		Iterator<Event> i=this.eventList.iterator();
		ArrayList<Event> selected=new ArrayList<Event>();
		while (i.hasNext()){
			Event e=i.next();
			if(e.getStatus()){
				e.unSelect();
				selected.add(e);
				i.remove();
			}
		}
		if(this.adapter!=null){
			adapter.notifyDataSetChanged();
		}
		return selected;
	}
	
	public int size(){
		return eventList.size();
	}
}
